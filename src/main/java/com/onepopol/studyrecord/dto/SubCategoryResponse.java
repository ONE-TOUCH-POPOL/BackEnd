package com.onepopol.studyrecord.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onepopol.studyrecord.repository.entity.SubCategory;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class SubCategoryResponse {

    private Long id;

    private String codeName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<StudyRecordDetail> studyRecordDetailList = new ArrayList<>();

    public void addStudyRecordDetail(StudyRecordDetail studyRecordDetail) {
        studyRecordDetailList.add(studyRecordDetail);
    }

    public SubCategoryResponse(SubCategory subCategory) {
        this.id = subCategory.getId();
        this.codeName = subCategory.getCodeName();
        this.studyRecordDetailList = new ArrayList<>();
    }
}
