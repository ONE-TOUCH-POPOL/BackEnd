package com.onepopol.studyrecord.service;

import com.onepopol.config.BaseException;
import com.onepopol.studyrecord.dto.CategoryAllResponse;
import com.onepopol.studyrecord.dto.MainCategoryCreate;
import com.onepopol.studyrecord.dto.SubCategoryCreate;
import com.onepopol.studyrecord.repository.MainCategoryRepository;
import com.onepopol.studyrecord.repository.SubCatergoryRepository;
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
    private final SubCatergoryRepository subCatergoryRepository;
    @PersistenceContext
    private EntityManager em;

    public Long addMainCategory(MainCategoryCreate mainCategoryCreate) {
        return mainCategoryRepository.save(mainCategoryCreate.toEntity()).getId();
    }

    public List<CategoryAllResponse> getCategoryAll() {
        List<MainCategory> mainCategories = mainCategoryRepository.findAll();
        return mainCategories.stream()
                .map(CategoryAllResponse::new)
                .collect(Collectors.toList());
    }

    public Long addSubCategory(SubCategoryCreate subCategoryCreate) {
        MainCategory mainCategory = em.find(MainCategory.class, subCategoryCreate.getMainCode());
//        MainCategory mainCategory = mainCategoryRepository.getReferenceById(subCategoryCreate.getMainCode());
        if (mainCategory == null) {
            throw new BaseException(new ApiError("메인 카테고리가 없습니다.", 2000));
        }
        mainCategory.addSubCategory(subCategoryCreate.toEntity());
        return 1L;
    }
}
