package org.example.offerpilot.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginVO {
    private Long userId;
    private String username;
    private String token;
}