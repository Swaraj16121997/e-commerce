package com.example.ecommerce.product.security;

import com.example.ecommerce.user.models.Role;
import com.example.ecommerce.user.models.Session;
import com.example.ecommerce.user.models.User;
import com.example.ecommerce.user.repositories.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import javax.crypto.SecretKey;
import java.util.*;

@Service
public class TokenValidator {

//    @Autowired
//    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    SessionRepository sessionRepository;

    public Optional<JwtObject> validateToken(String token){
//        RestTemplate restTemplate =  restTemplateBuilder.build();   // in a typical microservice model the user service shall be running in some other instance, and we should be calling the validation endpoint of the user auth service rest api using this rest template but as in this case it is monolithic we have to use it internally

        Optional<Session> sessionOptional = sessionRepository.findByToken(token);
        if (sessionOptional.isEmpty()) {
            return Optional.empty();
        }
        MacAlgorithm alg = Jwts.SIG.HS256;
        SecretKey key = alg.key().build();

        try {
            Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
            User user = claims.get("user", User.class);

            JwtObject jwtObject = new JwtObject();

            jwtObject.setUserId(user.getId());
            jwtObject.setEmail(user.getEmail());

            List<Role> roles = new ArrayList<>();
            roles.addAll(user.getRoles());
            jwtObject.setRoles(roles);

            return Optional.of(jwtObject);

        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
