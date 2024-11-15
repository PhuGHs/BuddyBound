package com.mobile.buddybound.service;

import com.mobile.buddybound.model.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findById(Long id);
    User getCurrentLoggedInUser();
}
