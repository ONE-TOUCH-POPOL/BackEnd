package com.onepopol.studyrecord.repository.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@IdClass(Badge.BadgePK.class)
@NoArgsConstructor
@Table(name = "badge")
public class Badge {

    @Id
    @Column(name = "studyrecord_id")
    private Long studyRecordId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "badge_category_code")
    private BadgeCategory badgeCategory;

    @Data
    public static class BadgePK implements Serializable {
        private Long studyRecordId;
        private BadgeCategory badgeCategory;

        public BadgePK() {
        }

        @Override
        public boolean equals(Object o) {
            BadgePK badgePK = (BadgePK) o;
            return this.studyRecordId.longValue() == badgePK.getStudyRecordId().longValue()
                    && this.badgeCategory.getId() == badgePK.getBadgeCategory().getId();
        }

        @Override
        public int hashCode() {
            return Objects.hash(studyRecordId, badgeCategory.getId());
        }
    }

    @Builder
    public Badge(Long studyRecordId, BadgeCategory badgeCategory) {
        this.studyRecordId = studyRecordId;
        this.badgeCategory = badgeCategory;
    }

}