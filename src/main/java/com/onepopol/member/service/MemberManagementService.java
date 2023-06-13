package com.onepopol.member.service;

import com.onepopol.config.BaseException;
import com.onepopol.member.dto.MemberSignupRequest;
import com.onepopol.member.repository.MemberRepository;
import com.onepopol.member.repository.entity.Member;
import com.onepopol.utils.ApiError;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.onepopol.member.error.MemberErrorCode.EXISTS_EMAIL;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberManagementService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public void registerUser(MemberSignupRequest memberSignupRequest) {

        // 이메일 중복 체크
        System.out.println(memberSignupRequest.getEmail());
        if(memberRepository.findByEmail(memberSignupRequest.getEmail()).isPresent()){
            throw new BaseException(new ApiError(EXISTS_EMAIL.getMessage(), EXISTS_EMAIL.getCode()));
        }

        String encodedPassword = encoder.encode(memberSignupRequest.getPassword());
        MemberSignupRequest newMemberSignupRequest = MemberSignupRequest.encodePassword(memberSignupRequest, encodedPassword);

        Member member = Member.registerUser(newMemberSignupRequest);
        memberRepository.save(member);
    }

    public boolean checkDuplicateEmail(String email){
        if(memberRepository.findByEmail(email).isPresent()){
            return false; // 중복 아님
        }
        return true; // 중복
    }
}
