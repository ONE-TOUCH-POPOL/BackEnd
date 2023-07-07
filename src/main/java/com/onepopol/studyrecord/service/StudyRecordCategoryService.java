package com.onepopol.studyrecord.service;

import com.onepopol.config.BaseException;
import com.onepopol.studyrecord.dto.MainCategoryCreate;
import com.onepopol.studyrecord.dto.MainCategoryResponse;
import com.onepopol.studyrecord.dto.SubCategoryCreate;
import com.onepopol.studyrecord.repository.MainCategoryRepository;
import com.onepopol.studyrecord.repository.SubCategoryRepository;
import com.onepopol.studyrecord.repository.entity.MainCategory;
import com.onepopol.utils.ApiError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyRecordCategoryService {
    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    @PersistenceContext
    private EntityManager em;

    public void addMainCategory(MainCategoryCreate mainCategoryCreate) {
        mainCategoryRepository.save(mainCategoryCreate.toEntity());
    }

    public List<MainCategoryResponse> getCategoryAll() {
        List<MainCategory> mainCategories = mainCategoryRepository.findAll();
        return mainCategories.stream()
                .map(MainCategoryResponse::new)
                .collect(Collectors.toList());
    }

    public void addSubCategory(SubCategoryCreate subCategoryCreate) {
        MainCategory mainCategory = em.find(MainCategory.class, subCategoryCreate.getMainCode());
        if (mainCategory == null) {
            throw new BaseException(new ApiError("메인 카테고리가 없습니다.", 2000));
        }
        mainCategory.addSubCategory(subCategoryCreate.toEntity());
    }
}
