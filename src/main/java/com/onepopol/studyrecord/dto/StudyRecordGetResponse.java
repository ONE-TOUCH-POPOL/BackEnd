package com.onepopol.studyrecord.dto;

import com.onepopol.studyrecord.repository.entity.Badge;
import com.onepopol.studyrecord.repository.entity.StudyRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudyRecordGetResponse {

    private Long mainCode;
    private String mainCodeName;
    private List<SubCategories> subCategories = new ArrayList<>();

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class SubCategories {
        private Long subCode;
        private String subCodeName;
        List<StudyRecordDetail> studyRecordDeatilList = new ArrayList<>();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class BadgeResponse {
        private Long badgeCode;
        private String badgeName;

        public BadgeResponse(Badge badge) {
            this.badgeCode = badge.getBadgeCategory().getId();
            this.badgeName = badge.getBadgeCategory().getBadgeName();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class StudyRecordDetail {
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

}
