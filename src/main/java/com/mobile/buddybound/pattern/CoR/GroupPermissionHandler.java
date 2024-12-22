package com.mobile.buddybound.pattern.CoR;

import com.mobile.buddybound.model.dto.AccessRequest;
import com.mobile.buddybound.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GroupPermissionHandler extends BasePermissionHandler {
    private final MemberRepository memberRepository;
    @Override
    public boolean checkAccess(AccessRequest request) {
        if (memberRepository.existsByUser_IdAndGroup_IdAndIsApprovedIsTrue(request.getViewerId(), request.getGroupId())) {
            return super.checkAccess(request);
        }
        return false;
    }
}
