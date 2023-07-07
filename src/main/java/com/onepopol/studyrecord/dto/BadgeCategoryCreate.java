package com.onepopol.studyrecord.dto;

import com.onepopol.studyrecord.repository.entity.BadgeCategory;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BadgeCategoryCreate {
    @NotBlank(message = "뱃지 이름이 빈칸입니다.")
    private String badgeName;

    public BadgeCategory toEntity() {
        return BadgeCategory.builder()
                .badgeName(badgeName)
                .build();
    }
}
