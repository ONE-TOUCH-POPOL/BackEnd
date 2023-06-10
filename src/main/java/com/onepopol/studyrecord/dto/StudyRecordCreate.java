package com.onepopol.studyrecord.dto;

import com.onepopol.studyrecord.repository.entity.StudyRecord;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudyRecordCreate {
    private String title;

    private String content;

    private String category;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate recordDate;

    public StudyRecord toEntity() {
        return StudyRecord.builder()
                .title(title)
                .content(content)
                .category(category)
                .recordDate(recordDate)
                .build();
    }
}
