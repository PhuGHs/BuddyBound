package com.mobile.buddybound.pattern.observer;

import com.mobile.buddybound.model.dto.LocationDto;
import com.mobile.buddybound.model.dto.LocationUpdateDto;
import com.mobile.buddybound.model.entity.Group;
import com.mobile.buddybound.repository.GroupRepository;
import com.mobile.buddybound.service.UserService;
import com.mobile.buddybound.service.WebsocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class GroupSubscriber implements Subscriber {
    private final UserService userService;
    private final GroupRepository groupRepository;
    private final WebsocketService websocketService;
    @Override
    public void updateLocation(LocationDto location) {
        var currentUser = userService.getCurrentLoggedInUser();
        List<Group> joinedGroup = groupRepository.findGroupByUser(currentUser.getId());
        LocationUpdateDto message = LocationUpdateDto.builder()
                .location(location)
                .blockedIds(currentUser.getBlockedRelationships().stream().map((value) -> value.getUser().getId()).toList())
                .build();
        joinedGroup.forEach(group -> {
            log.info("User with id {} updated location", currentUser.getId());
            websocketService.sendLocationUpdate(group.getId(), message);
        });
    }

    @Override
    public void turnOffLocation() {

    }
}
