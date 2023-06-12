package com.onepopol.member.service;

import com.onepopol.member.dto.MemberSignupRequest;
import com.onepopol.member.repository.MemberRepository;
import com.onepopol.member.repository.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberManagementService {

    private final MemberRepository memberRepository;

    @Transactional
    public void registerUser(MemberSignupRequest memberSignupRequest) {
        Member member = Member.registerUser(memberSignupRequest);
        memberRepository.save(member);
    }
}
