package com.onepopol.studyrecord.repository.entity;

import com.onepopol.studyrecord.repository.StudyRecordRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
class StudyRecordTest {
    @Autowired
    private StudyRecordRepository studyRecordRepository;

    @Test
    @Transactional
    public void testStudyRecord() {
        // Given
        String title = "테스트 제목";
        String content = "테스트 내용";
        LocalDate date = LocalDate.now();
        Long userId = 1L;

        StudyRecord studyRecord = new StudyRecord().builder()
                .title(title)
                .content(content)
                .recordDate(date)
                .userId(userId)
                .build();

        // When
        Long studyRecordId = studyRecordRepository.save(studyRecord).getId();


        // Then
        StudyRecord res = studyRecordRepository.getReferenceById(studyRecordId);
        Assertions.assertEquals(res.getTitle(), title);
        Assertions.assertEquals(res.getContent(), content);
        Assertions.assertEquals(res.getRecordDate(), date);
        Assertions.assertEquals(res.getUserId(), userId);

    }
}