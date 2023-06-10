package com.onepopol.member.service;

import com.onepopol.member.domain.User;
import com.onepopol.member.dto.AuthDto;
import com.onepopol.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void registerUser(AuthDto.SignupDto signupDto) {
        User user = User.registerUser(signupDto);
        userRepository.save(user);
    }
}
