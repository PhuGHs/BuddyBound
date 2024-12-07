package com.mobile.buddybound.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CodeVerificationRequest {
    @Length(min = 6, max = 6, message = "The code must be a 6 digit number")
    @Pattern(regexp = "^[0-9]{6}$", message = "The code must be a valid 6-digit number")
    private String code;
}
