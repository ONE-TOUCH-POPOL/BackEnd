package com.onepopol.studyrecord.repository.entity;

import com.onepopol.utils.TimeStamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "studyrecord")
public class StudyRecord extends TimeStamped {
    @Id
    @Column(name = "studyrecord_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String category;

    private LocalDateTime recordDate;

    @Builder
    public StudyRecord(String title, String content, String category, LocalDateTime recordDate) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.recordDate = recordDate;
    }
}
