package com.onepopol.member.repository.entity;

import com.onepopol.member.domain.Role;
import com.onepopol.member.dto.MemberSignupRequest;
import lombok.*;

import javax.persistence.*;


@Entity(name = "member")
@Getter
@Setter
@NoArgsConstructor()
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email; // Principal
    private String password; // Credential

    @Enumerated(EnumType.STRING)
    private Role role; // 사용자 권한

    // == 생성 메서드 == //
    public static Member registerUser(MemberSignupRequest memberSignupRequest) {
        Member member = new Member();

        member.email = memberSignupRequest.getEmail();
        member.password = memberSignupRequest.getPassword();
        member.role = Role.USER;

        return member;
    }

    @Builder
    public Member(Long id,String email,String password,Role role){
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;

    }
}

