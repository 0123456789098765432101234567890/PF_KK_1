package com.example.demo.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserListForm {
    @NotEmpty(message = "User ID is required")
    private String loginId;
    
    private String email;
    
    private String user_name;

     private String status;

    private boolean deleted;
}
