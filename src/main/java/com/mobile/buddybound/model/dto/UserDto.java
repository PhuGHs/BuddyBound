package com.mobile.buddybound.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    @JsonProperty("id")
    @JsonView({Views.Read.class, Views.Create.class})
    private Long id;

    @JsonProperty("phone_number")
    @JsonView({Views.Read.class, Views.Create.class})
    private String phoneNumber;

    @JsonProperty("full_name")
    @JsonView({Views.Read.class, Views.Create.class})
    private String fullName;

    @JsonProperty("gender")
    @JsonView({Views.Read.class, Views.Create.class})
    private boolean gender;
}
