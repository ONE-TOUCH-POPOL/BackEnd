package com.onepopol.studyrecord.dto;

import com.onepopol.studyrecord.repository.entity.MainCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudyRecordGetResponse {

    private Long mainCode;
    private String mainCodeName;
    private List<SubCategoryResponse> subCategories = new ArrayList<>();

    public StudyRecordGetResponse(MainCategory mainCategory) {
        this.mainCode = mainCategory.getId();
        this.mainCodeName = mainCategory.getCodeName();
    }
    
}
