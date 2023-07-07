package com.onepopol.studyrecord.dto;

import com.onepopol.studyrecord.repository.entity.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SubCategoryCreate {

    @NotNull(message = "메인 카테고리가 필요합니다.")
    private Long mainCode;

    @NotBlank(message = "서브 카테고리명이 빈캅입니다.")
    private String subCategoryName;

    public SubCategory toEntity() {
        return SubCategory
                .builder()
                .codeName(subCategoryName)
                .build();
    }
}
