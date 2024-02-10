package com.example.ecommerce.user.services;

import com.example.ecommerce.user.dtos.UserDto;
import com.example.ecommerce.user.models.Session;
import com.example.ecommerce.user.models.SessionStatus;
import com.example.ecommerce.user.models.User;
import com.example.ecommerce.user.repositories.SessionRepository;
import com.example.ecommerce.user.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public ResponseEntity<UserDto> login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        //User does not exist
        if (userOptional.isEmpty()) {
            return null;
        }

        User user = userOptional.get();

        //Validation
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return null;
        }

//        String token = RandomStringUtils.randomAlphanumeric(30);    // a normal token generation

        // JWT generation (containing some user info like userId, email, roles, etc. in the token itself)
//        String message = "{\n" +
//        "   \"email\": \"swaraj@gmail.com\",\n" +
//        "   \"roles\": [\n" +
//        "      \"admin\",\n" +
//        "      \"user\"\n" +
//        "   ],\n" +
//        "   \"expirationDate\": \"31stDecember2024\"\n" +
//        "}";    // we don't generally pass the user info or payload part of JWT in this way, we use a hashmap instead, this is just for basic testing purpose

//        byte[] content = message.getBytes(StandardCharsets.UTF_8);
//        String token = Jwts.builder().content(content).compact();   // the JWT generated here is without a signature (i.e., 3rd part of this JWT will be empty here)

        // for the header and signature part of the JWT creation
        MacAlgorithm alg = Jwts.SIG.HS256;
        SecretKey key = alg.key().build();  // generally the secret key used for signature creation is stored somewhere, but for testing purpose we are generating the secret key here itself

//        String token = Jwts.builder().content(content).signWith(key, alg).compact();    // the JWT created now, will have the header part(having the signature algo name) and the signature part, along with the payload

        // for the payload(user info) part of the JWT creation
        Map<String, Object> jsonForJwt = new HashMap<>();
        jsonForJwt.put("email", user.getEmail());
        jsonForJwt.put("roles", user.getRoles());
        jsonForJwt.put("expirationDate", new Date());
        jsonForJwt.put("createdAt" , new Date());

        String token = Jwts.builder().claims(jsonForJwt).signWith(key, alg).compact();

        // assigning token to the user across multiple requests within a session
        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        sessionRepository.save(session);

        UserDto userDto = User.from(user);  // one of the standard practices of converting types (for eg., here, from User to UseDto) by creating a from() method

//        Map<String, String> headers = new HashMap<>();
//        headers.put(HttpHeaders.SET_COOKIE, token);   // simple way of setting headers

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);     // setting headers

        ResponseEntity<UserDto> response = new ResponseEntity<>(userDto, headers, HttpStatus.OK);

        return response;
    }

    public ResponseEntity<Void> logout(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()) {
            return null;
        }

        Session session = sessionOptional.get();
        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);

        return ResponseEntity.ok().build();
    }

    public UserDto signUp(String email, String password) {
        User user = new User();
        user.setEmail(email);
//        user.setPassword(password);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        User savedUser = userRepository.save(user);

        return UserDto.from(savedUser);
    }

    public SessionStatus validate(String token, Long userId) {

        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);
        if (sessionOptional.isEmpty()) {
            return null;
        }

        MacAlgorithm alg = Jwts.SIG.HS256;
        SecretKey key = alg.key().build();

        // to validate the JWT
        try {
            Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }

        // if we want we can add more validations like (expiryDateTime > currentDateTime), check login device, etc.

        return SessionStatus.ACTIVE;
    }

}
