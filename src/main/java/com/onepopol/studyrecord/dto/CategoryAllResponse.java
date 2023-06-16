package com.onepopol.studyrecord.dto;

import com.onepopol.studyrecord.repository.entity.MainCategory;
import com.onepopol.studyrecord.repository.entity.SubCategory;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryAllResponse {
    private Long id;
    private String code_name;
    private List<SubCategoryResponse> subCategoryList;

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @AllArgsConstructor
    public static class SubCategoryResponse {
        private Long id;
        private String code_name;

        public SubCategoryResponse(SubCategory subCategory) {
            this.id = subCategory.getId();
            ;
            this.code_name = subCategory.getCode_name();
        }
    }

    public CategoryAllResponse(MainCategory mainCategory) {
        this.id = mainCategory.getId();
        this.code_name = mainCategory.getCode_name();
        this.subCategoryList = mainCategory.getSubCategory().stream()
                .map(SubCategoryResponse::new)
                .collect(Collectors.toList());
    }
}

