package com.onepopol.studyrecord.dto;

import com.onepopol.studyrecord.repository.entity.Badge;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BadgeResponse {
    private Long badgeCode;
    private String badgeName;

    public BadgeResponse(Badge badge) {
        this.badgeCode = badge.getBadgeCategory().getId();
        this.badgeName = badge.getBadgeCategory().getBadgeName();
    }
}