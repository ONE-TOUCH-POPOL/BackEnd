package com.onepopol.studyrecord.service;

import com.onepopol.studyrecord.repository.MainCategoryRepository;
import com.onepopol.studyrecord.repository.SubCatergoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudyRecordCategoryServiceTest {

    @Mock
    private MainCategoryRepository mainCategoryRepository;

    @Mock
    private SubCatergoryRepository subCatergoryRepository;

    @InjectMocks
    private StudyRecordCategoryService studyRecordCategoryService;

    @Test
    @DisplayName("모든 카테고리 종류를 불러온다.")
    public void getAllCategory() {

    }
}