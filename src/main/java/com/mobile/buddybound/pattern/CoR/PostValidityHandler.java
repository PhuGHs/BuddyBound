package com.mobile.buddybound.pattern.CoR;

import com.mobile.buddybound.model.dto.AccessRequest;
import com.mobile.buddybound.model.entity.Post;
import com.mobile.buddybound.repository.PostRepository;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class PostValidityHandler extends BasePermissionHandler {
    private final PostRepository postRepository;

    @Override
    public boolean checkAccess(AccessRequest request) {
        Post post = postRepository.findById(request.getPostId())
                .orElse(null);
        if (Objects.isNull(post)) {
            return false;
        }
        if (post.isExpired()) {
            if (request.getOwnerId().equals(request.getViewerId())) {
                return super.checkAccess(request);
            }
            return false;
        }
        return false;
    }
}
