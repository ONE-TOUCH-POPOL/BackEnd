package com.onepopol.member.repository.entity;

import com.onepopol.member.domain.Role;
import com.onepopol.member.dto.MemberSignupRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

//    @NotBlank(message = "이메일은 필수 입력 값입니다.")
//    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    private String email; // Principal

//    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
//    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
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
}

