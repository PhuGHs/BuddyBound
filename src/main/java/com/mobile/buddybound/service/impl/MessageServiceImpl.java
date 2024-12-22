package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.exception.BadRequestException;
import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.constants.ImageDirectory;
import com.mobile.buddybound.model.dto.MessageDto;
import com.mobile.buddybound.model.dto.MessagePostDto;
import com.mobile.buddybound.model.entity.Image;
import com.mobile.buddybound.model.entity.Member;
import com.mobile.buddybound.model.entity.Message;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.repository.ImageRepository;
import com.mobile.buddybound.repository.MemberRepository;
import com.mobile.buddybound.repository.MessageRepository;
import com.mobile.buddybound.repository.UserImageRepository;
import com.mobile.buddybound.service.*;
import com.mobile.buddybound.service.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
@Slf4j
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final MessageMapper messageMapper;
    private final UserService userService;
    private final ImageService imageService;
    private final GroupService groupService;
    private final ImageRepository imageRepository;
    private final WebsocketService websocketService;

    private final static String baseUrl = ImageDirectory.MESSAGE_PREFIX;

    @Override
    @Transactional
    @Cacheable(value = "messages", key = "#dto.getGroupId()")
    public ResponseEntity<?> sendAMessage(MessagePostDto dto, List<MultipartFile> images) {
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        Member member = memberRepository.getMemberByUser_IdAndGroup_Id(currentUserId, dto.getGroupId())
                .orElseThrow(() -> new NotFoundException("Member not found"));
        Message message = messageMapper.toEntityOther(dto, groupService);
        message.setMember(member);

        if (images != null && !images.isEmpty()) {
            // Use parallel stream to handle image uploads concurrently
            List<CompletableFuture<Image>> imageFutures = images.stream()
                    .map(file -> CompletableFuture.supplyAsync(() -> imageService.uploadImageAsync(file, baseUrl))).toList();

            // Collect all the uploaded images
            List<Image> messageImages = imageFutures.stream()
                    .map(CompletableFuture::join) // Wait for all futures to complete
                    .filter(Objects::nonNull)
                    .toList();

            if (!messageImages.isEmpty()) {
                message.setImages(imageRepository.saveAll(messageImages));
            }
        }

        message = messageRepository.save(message);
        MessageDto messageDto = messageMapper.toDto(message);
        //send websocket
        websocketService.sendMessage(messageDto);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Send messages", messageDto));
    }

    @Override
    @Cacheable(value = "messages", key = "#groupId")
    public ResponseEntity<?> getAllGroupMessages(Long groupId, Pageable pageable) {
        checkUserInvolved(groupId);
        var messageDtos = messageRepository.findByGroup_Id(groupId, pageable).stream().map(messageMapper::toDto);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get all group messages", messageDtos));
    }

    private void checkUserInvolved(Long groupId) {
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        if (!memberRepository.existsByUser_IdAndGroup_IdAndIsApprovedIsTrue(currentUserId, groupId)) {
            throw new NotFoundException("Can't get group messages because the you are not in the group");
        }
    }
}
