package com.onepopol.studyrecord.service;


import com.onepopol.studyrecord.dto.StudyRecordCreate;
import com.onepopol.studyrecord.dto.StudyRecordGetResponse;
import com.onepopol.studyrecord.repository.StudyRecordRepository;
import com.onepopol.studyrecord.repository.SubCategoryRepository;
import com.onepopol.studyrecord.repository.entity.MainCategory;
import com.onepopol.studyrecord.repository.entity.StudyRecord;
import com.onepopol.studyrecord.repository.entity.SubCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyRecordCrudService {
    private final StudyRecordRepository studyRecordRepository;
    private final SubCategoryRepository subCategoryRepository;
    @PersistenceContext
    private EntityManager em;

    public void addStudyRecord(StudyRecordCreate studyRecordCreate) {
        SubCategory subCategory = subCategoryRepository.getReferenceById(studyRecordCreate.getCodeValue());
        StudyRecord studyRecord = studyRecordCreate.toEntity();
        studyRecord.setSubCategory(subCategory);
        studyRecordRepository.save(studyRecord);
    }

    public List<StudyRecordGetResponse> getStudyRecordByUserId(Long userId) {

        Sort sort = Sort.by(
                new Sort.Order(Sort.Direction.ASC, "subCategory.mainCategory.id"),
                new Sort.Order(Sort.Direction.ASC, "subCategory.id")
        );
        List<StudyRecord> resStudyRecord = studyRecordRepository.findByMember_Id(userId, sort);
        List<StudyRecordGetResponse> studyRecordGetResponses = new ArrayList<>();

        Map<Long, Integer> mainCategoryIndex = new HashMap<>();
        Map<Long, Integer> subCategoryIndex = new HashMap<>();

        for (StudyRecord studyRecord : resStudyRecord) {
            SubCategory subCategory = studyRecord.getSubCategory();
            MainCategory mainCategory = subCategory.getMainCategory();

            // 메인 카테고리 셋팅
            int curMainIndex = mainCategoryIndex.getOrDefault(mainCategory.getId(), studyRecordGetResponses.size());
            // 넣은적 없는 카테고리 일 경우 카테고리 인덱스를 Map 에 넣어줌
            if (curMainIndex == studyRecordGetResponses.size()) {
                mainCategoryIndex.put(mainCategory.getId(), curMainIndex);
            }
            //
            if (curMainIndex == studyRecordGetResponses.size()) {
                studyRecordGetResponses.add(new StudyRecordGetResponse());
            }

            StudyRecordGetResponse studyRecordGetResponse = studyRecordGetResponses.get(curMainIndex);
            studyRecordGetResponse.setMainCode(mainCategory.getId());
            studyRecordGetResponse.setMainCodeName(mainCategory.getCode_name());

            // 서브 카테고리 셋팅
            List<StudyRecordGetResponse.SubCategories> subCategoriesList = studyRecordGetResponse.getSubCategories();
            int curSubIndex = subCategoryIndex.getOrDefault(subCategory.getId(), subCategoriesList.size());
            if (curSubIndex == subCategoriesList.size()) {
                subCategoryIndex.put(subCategory.getId(), curSubIndex);
            }
            if (curSubIndex == subCategoriesList.size()) {
                subCategoriesList.add(new StudyRecordGetResponse.SubCategories());
            }
            StudyRecordGetResponse.SubCategories subCategories = subCategoriesList.get(curSubIndex);
            subCategories.setSubCode(subCategory.getId());
            subCategories.setSubCodeName(subCategory.getCode_name());

            // 디테일 부분 셋팅
            List<StudyRecordGetResponse.StudyRecordDetail> studyRecordDetailList = subCategories.getStudyRecordDeatilList();
            studyRecordDetailList.add(new StudyRecordGetResponse.StudyRecordDetail(studyRecord));
        }
        return studyRecordGetResponses;
    }
}
