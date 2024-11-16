package com.mobile.buddybound.model.response;

import com.fasterxml.jackson.annotation.JsonView;
import com.mobile.buddybound.model.dto.Views;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    @JsonView({Views.Read.class, Views.Create.class, Views.Update.class})
    private ApiResponseStatus status;
    @JsonView({Views.Read.class, Views.Create.class, Views.Update.class})
    private String message;
    @JsonView({Views.Read.class, Views.Create.class, Views.Update.class})
    private Object data;
}
