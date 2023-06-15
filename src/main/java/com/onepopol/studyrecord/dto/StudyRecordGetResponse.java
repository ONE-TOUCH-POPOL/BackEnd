package com.onepopol.studyrecord.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudyRecordGetResponse {
    private Long id;
    private String title;

    private String content;

    private String category;

    private Long memberId;

    private LocalDate recordDate;

    public StudyRecordGetResponse(Long id, String title, String content, String category, LocalDate recordDate, Long memberId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.category = category;
        this.recordDate = recordDate;
    }
}
