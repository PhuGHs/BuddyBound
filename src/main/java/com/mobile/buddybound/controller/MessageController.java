package com.mobile.buddybound.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mobile.buddybound.controller.validation.ContentType;
import com.mobile.buddybound.model.constants.ContentTypes;
import com.mobile.buddybound.model.dto.MessagePostDto;
import com.mobile.buddybound.model.dto.Views;
import com.mobile.buddybound.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/messages")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/get-group-messages/{groupId}")
    @JsonView(Views.Read.class)
    public ResponseEntity<?> getGroupMessages(@PathVariable Long groupId, Pageable pageable) {
        return messageService.getAllGroupMessages(groupId, pageable);
    }

    @PostMapping(value = "/sendMessage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @JsonView(Views.Read.class)
    public ResponseEntity<?> sendMessage(
            @RequestPart MessagePostDto dto,
            @RequestPart(value = "images", required = false)
            List<MultipartFile> images
    ) {
        return messageService.sendAMessage(dto, images);
    }
}
