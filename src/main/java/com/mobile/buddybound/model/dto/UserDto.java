package com.mobile.buddybound.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    @JsonProperty("id")
    @JsonView({Views.Read.class, Views.Create.class})
    private Long id;

    @JsonView({Views.Read.class, Views.Create.class})
    private String phoneNumber;

    @JsonView({Views.Read.class, Views.Create.class})
    private String fullName;

    @JsonView({Views.Read.class, Views.Create.class})
    private boolean gender;

    @JsonView({Views.Read.class})
    private String avatar;
}
