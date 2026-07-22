package com.app.ecom.dto;

import com.app.ecom.Entity.UserRole;
import lombok.Data;

@Data
public class UserRequest {
    private String id;
    private String firstName;
    private String lastName;
    private  String email;
    private  String phone;
    private UserRole role;
    private AdressDto adress;
}
