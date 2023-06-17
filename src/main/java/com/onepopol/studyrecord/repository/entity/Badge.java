package com.onepopol.studyrecord.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@IdClass(Badge.BadgePK.class)
@Table(name = "badge")
public class Badge {

    @Id
    @Column(name = "studyrecord_id")
    private Long studyRecordId;

    @ManyToOne
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
    
}