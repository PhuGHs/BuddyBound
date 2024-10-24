package com.mobile.buddybound.model.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDto {
    @Email(message = "Email is invalid")
    private String email;

    @Length(min = 6, max = 50, message = "Password length must be more than or equal 6 and less than or equal 50")
    private String password;
}
