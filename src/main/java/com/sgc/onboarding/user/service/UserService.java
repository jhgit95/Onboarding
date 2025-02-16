package com.sgc.onboarding.user.service;

import com.sgc.onboarding.user.entity.User;
import com.sgc.onboarding.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserFromUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserFromNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

}
