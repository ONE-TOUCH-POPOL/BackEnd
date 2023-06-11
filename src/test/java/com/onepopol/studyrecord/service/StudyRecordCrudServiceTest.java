package com.onepopol.studyrecord.service;

import com.onepopol.studyrecord.dto.StudyRecordCreate;
import com.onepopol.studyrecord.repository.StudyRecordRepository;
import com.onepopol.studyrecord.repository.entity.StudyRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class StudyRecordCrudServiceTest {

    @Mock
    private StudyRecordRepository studyRecordRepository;

    private StudyRecordCrudService studyRecordCrudService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        studyRecordCrudService = new StudyRecordCrudService(studyRecordRepository);
    }

    @Test
    void addStudyRecord() {
        // 테스트에 필요한 입력 데이터 설정
        StudyRecordCreate studyRecordCreate = new StudyRecordCreate();
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

        StudyRecord savedStudyRecord = new StudyRecord();
        savedStudyRecord.setId(1L); // 예상되는 저장된 엔티티의 식별자(ID) 값을 설정

        // Mocking save() 메서드의 동작 설정
        when(studyRecordRepository.save(Mockito.any())).thenReturn(savedStudyRecord);

        // 메서드 호출 및 결과 확인
        Long actualId = studyRecordCrudService.addStudyRecord(studyRecordCreate);
        assertEquals(1L, actualId);
    }

    @Test
    void getStudyRecordByUserId() {
    }
}