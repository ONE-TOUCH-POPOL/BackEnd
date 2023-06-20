package com.onepopol.studyrecord.repository.entity;

import com.onepopol.constant.Status;
import com.onepopol.member.repository.MemberRepository;
import com.onepopol.member.repository.entity.Member;
import com.onepopol.studyrecord.repository.StudyRecordRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
class StudyRecordTest {
    @Autowired
    private StudyRecordRepository studyRecordRepository;
    private final static Long USER_ID = 1L;
    private static Member member;

    @BeforeTestClass
    public static void onlyOnce(@Autowired MemberRepository memberRepository) {
        member = new Member().builder()
                .id(USER_ID)
                .build();
        memberRepository.save(member);
    }

    @Test
    @Transactional
    @DisplayName("학습기록 작성 테스트")
    public void testStudyRecord() {
        // Given
        String title = "테스트 제목";
        String content = "테스트 내용";
        LocalDate date = LocalDate.now();

        StudyRecord studyRecord = new StudyRecord().builder()
                .title(title)
                .content(content)
                .recordDate(date)
                .build();

        // When
        Long studyRecordId = studyRecordRepository.save(studyRecord).getId();


        // Then
        StudyRecord res = studyRecordRepository.getReferenceById(studyRecordId);
        Assertions.assertEquals(studyRecord, res);

    }

    @Test
    @Transactional
    @DisplayName("학습기록 저장시 Status의 기본값은 Active")
    public void testStudyRecordSaveDefaultStatusIsActive() {
        // Given
        String title = "테스트 제목";
        String content = "테스트 내용";
        LocalDate date = LocalDate.now();

        StudyRecord studyRecord = new StudyRecord().builder()
                .title(title)
                .content(content)
                .recordDate(date)
                .member(member)
                .build();
        // When
        Long studyRecordId = studyRecordRepository.save(studyRecord).getId();

        // Then
        StudyRecord res = studyRecordRepository.getReferenceById(studyRecordId);
        Assertions.assertEquals(res.getStatus().getValue(), Status.ACTIVE.getValue());

    }

    @Test
    @Transactional
    @DisplayName("Status값을 임의로 변경")
    public void testStudyRecordChangeStatusValue() {
        // Given
        String title = "테스트 제목";
        String content = "테스트 내용";
        LocalDate date = LocalDate.now();

        StudyRecord studyRecord = new StudyRecord().builder()
                .title(title)
                .content(content)
                .recordDate(date)
                .member(member)
                .build();
        // When
        Long studyRecordId = studyRecordRepository.save(studyRecord).getId();
        StudyRecord res = studyRecordRepository.getReferenceById(studyRecordId);
        res.setStatus(Status.INACTIVE);

        // Then
        StudyRecord changeRes = studyRecordRepository.getReferenceById(studyRecordId);
        Assertions.assertEquals(changeRes.getStatus().getValue(), Status.INACTIVE.getValue());

    }
}