package com.onepopol.studyrecord.repository;

import com.onepopol.studyrecord.repository.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Badge.BadgePK> {

}
