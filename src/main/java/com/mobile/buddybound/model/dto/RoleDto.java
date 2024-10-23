package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RoleDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("role_name")
    private String roleName;
    @JsonIgnore
    private String description;
}
