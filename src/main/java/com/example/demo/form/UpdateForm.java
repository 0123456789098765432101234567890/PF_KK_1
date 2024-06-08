package com.example.demo.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateForm {
    @NotEmpty(message = "めいるあどれすはひっすです" /* "Email is required" */)
    @Email
    @Size(max = 255, message = "255もじいかでたのむ")
    private String email;

	@NotEmpty(message = "ぱすわあどはひっすです" /* "Password is required" */)
    @Size(min = 3, max = 32, message = "3から32もじでたのむ")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Password can only contain alphanumeric characters, dashes, and underscores")
    private String pass;
	
	
}
