package com.onepopol.member.security;

import com.onepopol.member.repository.entity.Member;
import com.onepopol.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public MemberDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find user with this email. -> " + email));

        if(findMember != null){
            MemberDetailsImpl userDetails = new MemberDetailsImpl(findMember);
            return  userDetails;
        }

        return null;
    }
}
