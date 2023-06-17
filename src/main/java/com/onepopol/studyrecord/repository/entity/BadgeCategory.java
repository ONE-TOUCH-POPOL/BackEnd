package com.onepopol.studyrecord.repository.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "badge_category")
public class BadgeCategory {
    @Id
    @Column(name = "badge_category_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String badgeName;

    @Builder
    public BadgeCategory(String badgeName) {
        this.badgeName = badgeName;
    }
}
