package com.mobile.buddybound.service;

import com.mobile.buddybound.model.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    User register();
}
