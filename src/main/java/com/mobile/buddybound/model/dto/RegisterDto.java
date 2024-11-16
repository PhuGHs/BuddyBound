package com.mobile.buddybound.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDto {
    @Email(message = "Email is invalid")
    private String email;

    @Length(min = 6, max = 50, message = "Password length must be more than or equal 6 and less than or equal 50")
    private String password;

    @NotBlank(message = "fullName is required")
    private String fullName;

    private boolean gender;

    @NotNull(message = "This is required")
    private LocalDate birthday;
}
