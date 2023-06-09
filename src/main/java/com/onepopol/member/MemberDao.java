package com.onepopol.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDao extends JpaRepository<Member, String> {

}