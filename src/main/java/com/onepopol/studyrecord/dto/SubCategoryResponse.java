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
    private String codeName;

    public SubCategoryResponse(SubCategory subCategory) {
        this.id = subCategory.getId();
        this.codeName = subCategory.getCodeName();
    }
}
