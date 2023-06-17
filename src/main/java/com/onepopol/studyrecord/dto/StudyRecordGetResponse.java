package com.onepopol.studyrecord.dto;

import com.onepopol.studyrecord.repository.entity.StudyRecord;
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

    public StudyRecordGetResponse(StudyRecord studyRecord) {
        this.id = studyRecord.getId();
        this.title = studyRecord.getTitle();
        this.content = studyRecord.getContent();
        this.memberId = studyRecord.getMember().getId();
        this.category = studyRecord.getCategory();
        this.recordDate = studyRecord.getRecordDate();
    }

}
