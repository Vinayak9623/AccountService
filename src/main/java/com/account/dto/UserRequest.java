package com.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private UUID id;
    private String name;
    private String email;
    private List<String> roles;
    private String mobileNumber;
}
