package com.onepopol.studyrecord.repository.entity;

import com.onepopol.member.repository.entity.Member;
import com.onepopol.utils.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "studyrecord")
public class StudyRecord extends BaseEntity {
    @Id
    @Column(name = "studyrecord_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    private String category;

    private LocalDate recordDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @Builder
    public StudyRecord(String title, String content, String category, LocalDate recordDate, Member member) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.recordDate = recordDate;
        this.member = member;
    }
}
