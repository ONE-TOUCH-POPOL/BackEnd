package com.onepopol.studyrecord.service;

import com.onepopol.member.repository.entity.Member;
import com.onepopol.studyrecord.dto.StudyRecordCreate;
import com.onepopol.studyrecord.repository.StudyRecordRepository;
import com.onepopol.studyrecord.repository.entity.StudyRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudyRecordCrudServiceTest {

    @Mock
    private StudyRecordRepository studyRecordRepository;

    @InjectMocks
    private StudyRecordCrudService studyRecordCrudService;


    @Test
    @DisplayName("학습기록 저장 정상 작동")
    void addStudyRecord() {
        // 테스트에 필요한 입력 데이터 설정
        StudyRecordCreate studyRecordCreate = new StudyRecordCreate();
        String title = "테스트 제목";
        String content = "테스트 내용";
        LocalDate date = LocalDate.now();
        Long userId = 1L;

        Member member = new Member().builder()
                .id(userId)
                .build();

        StudyRecord studyRecord = new StudyRecord().builder()
                .title(title)
                .content(content)
                .recordDate(date)
                .member(member)
                .build();

        StudyRecord savedStudyRecord = new StudyRecord();
        savedStudyRecord.setId(1L); // 예상되는 저장된 엔티티의 식별자(ID) 값을 설정

        // Mocking save() 메서드의 동작 설정
        when(studyRecordRepository.save(Mockito.any())).thenReturn(savedStudyRecord);

        // 메서드 호출 및 결과 확인
        studyRecordCrudService.addStudyRecord(studyRecordCreate);
//        assertEquals(1L);
    }

    @Test
    @DisplayName("4개의 데이터를 반환함")
    void getStudyRecordByUserId() {
        List<StudyRecord> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            StudyRecord studyRecord = new StudyRecord();
            Member member = new Member();
            member.setId((i + 1L));
            studyRecord.setMember(member);
            list.add(studyRecord);
        }
        Long userId = 1L;

//        when(studyRecordRepository.findByMember_Id(userId)).thenReturn(list);

//        assertEquals(studyRecordCrudService.getStudyRecordByUserId(userId).size(), list.size());

    }
}