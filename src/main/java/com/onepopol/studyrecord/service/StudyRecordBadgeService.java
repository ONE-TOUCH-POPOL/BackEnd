package com.onepopol.studyrecord.service;

import com.onepopol.config.BaseException;
import com.onepopol.studyrecord.dto.BadgeCategoryCreate;
import com.onepopol.studyrecord.repository.BadgeCategoryRepository;
import com.onepopol.studyrecord.repository.BadgeRepository;
import com.onepopol.utils.ApiError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyRecordBadgeService {
    private final BadgeRepository badgeRepository;
    private final BadgeCategoryRepository badgeCategoryRepository;

    @PersistenceContext
    private EntityManager em;

    public void addBadgeCategory(BadgeCategoryCreate badgeCreate) {
        if (badgeCategoryRepository.existsByBadgeName(badgeCreate.getBadgeName())) {
            throw new BaseException(new ApiError("중복된 뱃지 이름이 있습니다.", 2001));
        }
        badgeCategoryRepository.save(badgeCreate.toEntity());
    }
}
