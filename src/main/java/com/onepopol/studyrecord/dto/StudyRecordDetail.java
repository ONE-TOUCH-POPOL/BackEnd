package com.onepopol.studyrecord.dto;

import com.onepopol.studyrecord.repository.entity.StudyRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudyRecordDetail {
    private Long id;

    private String title;

    private String content;

    private Long memberId;

    private LocalDate recordDate;

    private List<BadgeResponse> badges;

    public StudyRecordDetail(StudyRecord studyRecord) {
        this.id = studyRecord.getId();
        this.title = studyRecord.getTitle();
        this.content = studyRecord.getContent();
        this.memberId = studyRecord.getMember().getId();
        this.recordDate = studyRecord.getRecordDate();
    }
}
