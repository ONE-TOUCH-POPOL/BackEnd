package com.onepopol.studyrecord.dto;

import com.onepopol.member.repository.entity.Member;
import com.onepopol.studyrecord.repository.entity.StudyRecord;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudyRecordCreate {
    @NotBlank(message = "제목이 빈칸입니다.")
    private String title;

    @NotBlank(message = "내용이 빈칸입니다.")
    private String content;

    @NotNull(message = "서브 카테고리 번호가 필요합니다.")
    private Long codeValue;

    private List<BadgeCreate> badges;

    private Member member;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate recordDate;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class BadgeCreate {
        private Long badgeCode;
    }

    public StudyRecord toEntity() {
        return StudyRecord.builder()
                .title(title)
                .content(content)
                .member(member)
                .recordDate(recordDate)
                .build();
    }
}
