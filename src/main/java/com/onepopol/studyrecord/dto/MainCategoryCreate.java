package com.onepopol.studyrecord.dto;

import com.onepopol.studyrecord.repository.entity.MainCategory;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MainCategoryCreate {
    @NotBlank(message = "메인 카테고리명이 빈칸입니다.")
    private String codeName;

    public MainCategory toEntity() {
        return MainCategory.builder()
                .codeName(codeName)
                .build();
    }
}
