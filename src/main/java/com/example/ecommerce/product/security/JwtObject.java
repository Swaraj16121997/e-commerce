package com.example.ecommerce.product.security;

import com.example.ecommerce.user.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class JwtObject {
    private String email;
    private Long userId;
    private Date createdAt;
    private Date expiredAt;
    List<Role> roles = new ArrayList<>();
}
