package com.onepopol.studyrecord.repository.entity;

import com.onepopol.member.repository.entity.Member;
import com.onepopol.utils.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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

    private LocalDate recordDate;

    @ManyToOne()
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sub_code")
    private SubCategory subCategory;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "studyrecord_id")
    private List<Badge> badges;

    @Builder
    public StudyRecord(String title, String content, SubCategory subCategory, LocalDate recordDate, Member member, List<Badge> badges) {
        this.title = title;
        this.content = content;
        this.recordDate = recordDate;
        this.member = member;
        this.subCategory = subCategory;
        this.badges = badges;
    }
}
