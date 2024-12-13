package com.mobile.buddybound.pattern.observer;

import com.mobile.buddybound.model.dto.LocationDto;
import com.mobile.buddybound.model.entity.Group;
import com.mobile.buddybound.repository.GroupRepository;
import com.mobile.buddybound.service.UserService;
import com.mobile.buddybound.service.WebsocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class GroupSubscriber implements Subscriber {
    private final UserService userService;
    private final GroupRepository groupRepository;
    private final WebsocketService websocketService;
    @Override
    public void updateLocation(LocationDto location) {
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        List<Group> joinedGroup = groupRepository.findGroupByUser(currentUserId);
        joinedGroup.forEach(group -> {
            websocketService.sendLocationUpdate(group.getId(), location);
        });
    }

    @Override
    public void turnOffLocation() {

    }
}
