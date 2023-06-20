package com.onepopol.studyrecord.dto;

import com.onepopol.studyrecord.repository.entity.SubCategory;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class SubCategoryResponse {
    private Long id;
    private String code_name;

    public SubCategoryResponse(SubCategory subCategory) {
        this.id = subCategory.getId();
        this.code_name = subCategory.getCode_name();
    }
}
