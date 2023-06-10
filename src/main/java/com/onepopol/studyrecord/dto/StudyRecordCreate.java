package com.onepopol.studyrecord.dto;

import com.onepopol.studyrecord.repository.entity.StudyRecord;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

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

    private String category;

    private Long user_id;

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
