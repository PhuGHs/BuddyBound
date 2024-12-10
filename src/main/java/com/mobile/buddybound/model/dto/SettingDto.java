package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SettingDto {
    @JsonView(Views.Read.class)
    private Long id;

    @JsonView({Views.Read.class})
    private Long userId;

    @JsonView({Views.Read.class, Views.Update.class, Views.Create.class})
    private boolean contactEnabled;

    @JsonView({Views.Read.class, Views.Update.class, Views.Create.class})
    private boolean locationEnabled;

    @JsonView({Views.Read.class, Views.Update.class, Views.Create.class})
    private boolean locationHistoryEnabled;
}
