package com.onepopol.studyrecord.repository;

import com.onepopol.studyrecord.repository.entity.BadgeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeCategoryRepository extends JpaRepository<BadgeCategory, Long> {
    boolean existsByBadgeName(String badgeName);
}
