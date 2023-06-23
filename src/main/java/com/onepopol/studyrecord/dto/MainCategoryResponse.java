package com.onepopol.studyrecord.dto;

import com.onepopol.studyrecord.repository.entity.MainCategory;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MainCategoryResponse {
    
    private Long id;

    private String code_name;

    private List<SubCategoryResponse> subCategoryList;

    public MainCategoryResponse(MainCategory mainCategory) {
        this.id = mainCategory.getId();
        this.code_name = mainCategory.getCodeName();
        this.subCategoryList = mainCategory.getSubCategory().stream()
                .map(SubCategoryResponse::new)
                .collect(Collectors.toList());
    }
}

