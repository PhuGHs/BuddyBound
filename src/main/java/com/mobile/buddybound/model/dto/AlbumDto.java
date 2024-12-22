package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AlbumDto {
    @JsonView({Views.Read.class, Views.Update.class})
    private Long id;

    @JsonView({Views.Read.class, Views.Create.class, Views.Update.class})
    private String title;

    @JsonView({Views.Read.class})
    private UserDto user;

    @JsonView({Views.Read.class})
    private List<PostDto> posts;

    @JsonView({Views.Create.class, Views.Update.class})
    private List<Long> postIdList;

    @JsonView({Views.Read.class})
    private LocalDateTime createdAt = LocalDateTime.now();

}
