package com.mobile.buddybound.service.impl;

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
import com.mobile.buddybound.service.GroupService;
import com.mobile.buddybound.service.ImageService;
import com.mobile.buddybound.service.MessageService;
import com.mobile.buddybound.service.UserService;
import com.mobile.buddybound.service.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final MessageMapper messageMapper;
    private final UserService userService;
    private final ImageService imageService;
    private final GroupService groupService;
    private final ImageRepository imageRepository;

    private final static String baseUrl = ImageDirectory.MESSAGE_PREFIX;

    @Override
    @Transactional
    public ResponseEntity<?> sendAMessage(MessagePostDto dto, List<MultipartFile> images) {
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        Member member = memberRepository.getMemberByUser_IdAndGroup_Id(currentUserId, dto.getGroupId())
                .orElseThrow(() -> new NotFoundException("Member not found"));
        Message message = messageMapper.toEntityOther(dto, groupService);
        message.setMember(member);
        if (!Objects.isNull(images)) {
            List<Image> messageImages = images.stream().map(file -> {
                try {
                    return Image.builder()
                            .imageUrl(imageService.uploadImage(file, baseUrl))
                            .build();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).toList();
            message.setImages(imageRepository.saveAll(messageImages));
        }
        //2483
        message = messageRepository.save(message);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Send messages", messageMapper.toDto(message)));
    }

    @Override
    public ResponseEntity<?> getAllGroupMessages(Long groupId, Pageable pageable) {
        checkUserInvolved(groupId);
        var messageDtos = messageRepository.findByGroup_Id(groupId, pageable).stream().map(messageMapper::toDto);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get all group messages", messageDtos));
    }

    private void checkUserInvolved(Long groupId) {
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        if (!memberRepository.existsByUser_IdAndGroup_Id(currentUserId, groupId)) {
            throw new NotFoundException("Can't get group messages because the you are not in the group");
        }
    }
}
