package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class MessageDto {
    @JsonView(Views.Read.class)
    private Long id;

    @JsonView({ Views.Read.class, Views.Create.class })
    private Long groupId;

    @JsonView(Views.Create.class)
    private Long senderId;

    @JsonView(Views.Read.class)
    private MemberDto member;

    @JsonView({ Views.Create.class, Views.Read.class })
    @NotEmpty(message = "Content is required!")
    @NotNull(message = "Content is required!")
    private String content;

    @JsonView(Views.Read.class)
    private List<ImageDto> images;

    @JsonView(Views.Read.class)
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonView(Views.Read.class)
    private LocalDateTime updatedAt = LocalDateTime.now();

}
