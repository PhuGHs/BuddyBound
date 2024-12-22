package com.mobile.buddybound.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateProfileDto {
    @NotNull(message = "You must provide userId")
    private Long userId;
    @NotBlank(message = "fullName is required")
    private String fullName;

    private boolean gender;

    private String phoneNumber;

    @NotNull(message = "This is required")
    private LocalDate birthday;
}
