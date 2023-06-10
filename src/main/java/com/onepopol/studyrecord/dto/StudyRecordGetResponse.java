package com.onepopol.studyrecord.dto;

import com.onepopol.studyrecord.repository.entity.StudyRecord;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudyRecordGetResponse {
    private String title;

    private String content;

    private String category;

    private Long user_id;

    private LocalDate recordDate;

    public StudyRecordGetResponse(StudyRecord studyRecord) {
        this.title = studyRecord.getTitle();
        this.content = studyRecord.getContent();
        this.user_id = studyRecord.getUserId();
        this.category = studyRecord.getCategory();
        this.recordDate = studyRecord.getRecordDate();
    }
}
