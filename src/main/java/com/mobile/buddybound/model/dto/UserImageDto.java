package com.mobile.buddybound.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserImageDto {
    private Long id;
    private ImageDto image;
    private boolean mainAvatar;
}
