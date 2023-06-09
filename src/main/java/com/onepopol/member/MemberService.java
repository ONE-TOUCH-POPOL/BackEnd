package com.onepopol.member;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService implements UserDetailsService {

    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberDao memberDao, PasswordEncoder passwordEncoder) {
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
    }

    public void memberRegister(Member.SaveRequest member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        memberDao.save(member.toEntity());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberDao.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("username"));

        return toUserDetails(member);
    }

    private UserDetails toUserDetails(Member member) {
        return User.builder()
                .username(member.getId())
                .password(member.getPassword())
                .authorities(new SimpleGrantedAuthority(member.getAuthority().getName()))
                .build();
    }
}
