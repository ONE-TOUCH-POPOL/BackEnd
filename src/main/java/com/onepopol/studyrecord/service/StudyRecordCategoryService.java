package com.onepopol.studyrecord.service;

import com.onepopol.studyrecord.dto.MainCategoryCreate;
import com.onepopol.studyrecord.repository.MainCategoryRepository;
import com.onepopol.studyrecord.repository.SubCatergoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyRecordCategoryService {
    private final MainCategoryRepository mainCategoryRepository;
    private final SubCatergoryRepository subCatergoryRepository;

    public Long addMainCategory(MainCategoryCreate mainCategoryCreate) {
        return mainCategoryRepository.save(mainCategoryCreate.toEntity()).getId();
    }
}
