package com.onepopol.studyrecord.service;

import com.onepopol.member.repository.entity.Member;
import com.onepopol.studyrecord.dto.StudyRecordCreate;
import com.onepopol.studyrecord.repository.StudyRecordRepository;
import com.onepopol.studyrecord.repository.entity.StudyRecord;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class StudyRecordCrudServiceTest {

    @Mock
    private StudyRecordRepository studyRecordRepository;

    @InjectMocks
    private StudyRecordCrudService studyRecordCrudService;

    @Test
    @DisplayName("학습기록 저장 정상 작동")
    @Disabled
    void addStudyRecord() {
        // 테스트에 필요한 입력 데이터 설정
        StudyRecordCreate studyRecordCreate = new StudyRecordCreate();
        String title = "테스트 제목";
        String content = "테스트 내용";
        LocalDate date = LocalDate.now();
        Long userId = 1L;

        Member member = Member.builder()
                .id(userId)
                .build();

        StudyRecord studyRecord = StudyRecord.builder()
                .title(title)
                .content(content)
                .recordDate(date)
                .member(member)
                .build();

        StudyRecord savedStudyRecord = new StudyRecord();
        savedStudyRecord.setId(1L); // 예상되는 저장된 엔티티의 식별자(ID) 값을 설정

        // Mocking save() 메서드의 동작 설정
//        when(studyRecordRepository.save(Mockito.any())).thenReturn(savedStudyRecord);

        // 메서드 호출 및 결과 확인
//        studyRecordCrudService.addStudyRecord(studyRecordCreate);
//        assertEquals(1L);
    }

    @Test
    @DisplayName("4개의 데이터를 dto 로 변환후 반환")
    void getStudyRecordByUserId() {
//        Sort sort = Sort.by(
//                new Sort.Order(Sort.Direction.ASC, "subCategory.mainCategory.id"),
//                new Sort.Order(Sort.Direction.ASC, "subCategory.id")
//        );
//
//        List<StudyRecordGetResponse> studyRecordGetResponses = new ArrayList<>();
//        StudyRecordGetResponse studyRecordGetResponse = new StudyRecordGetResponse();
//        studyRecordGetResponse.setMainCode(1L);
//        studyRecordGetResponse.setMainCodeName("메인");
//        List<StudyRecordGetResponse.SubCategories> subCategories = new ArrayList<>();
//        studyRecordGetResponse.setSubCategories(subCategories);
//        StudyRecordGetResponse.SubCategories subCategories1 = new StudyRecordGetResponse.SubCategories();
//        subCategories1.setSubCode(1L);
//        subCategories1.setSubCodeName("서브");
//        subCategories1.setStudyRecordDeatilList(new ArrayList<>());
//        subCategories.add(subCategories1);
//
//        List<StudyRecord> list = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            // 뱃지
//            List<Badge> badges = new ArrayList<>();
//            BadgeCategory badgeCategory = BadgeCategory.builder()
//                    .badgeName("뱃지")
//                    .build();
//            badgeCategory.setId(1L);
//            Badge badge = Badge.builder()
//                    .badgeCategory(badgeCategory)
//                    .studyRecordId(new Long(1L))
//                    .build();
//            badges.add(badge);
//
//            //카테고리
//            MainCategory mainCategory = MainCategory.builder()
//                    .codeName("메인")
//                    .build();
//            mainCategory.setId(new Long(1L));
//            SubCategory subCategory = SubCategory.builder()
//                    .codeName("서브")
//                    .mainCategory(mainCategory)
//                    .build();
//            subCategory.setId(new Long(1L));
//
//
//            // 유저
//            Member member = new Member();
//            member.setId(1L);
//
//            // 기록 저장
//            StudyRecord studyRecord = StudyRecord.builder()
//                    .title("제목")
//                    .content("내용")
//                    .recordDate(LocalDate.now())
//                    .badges(badges)
//                    .subCategory(subCategory)
//                    .member(member)
//                    .build();
//            studyRecord.setMember(member);
//            studyRecord.setId(new Long(i));
//
//            List<StudyRecordGetResponse.StudyRecordDetail> studyRecordDetailList = subCategories1.getStudyRecordDeatilList();
//            StudyRecordGetResponse.StudyRecordDetail studyRecordDetail = new StudyRecordGetResponse.StudyRecordDetail(studyRecord);
//            List<StudyRecordGetResponse.BadgeResponse> badgeResponses = new ArrayList<>();
//            badgeResponses.add(new StudyRecordGetResponse.BadgeResponse(badge));
//            studyRecordDetail.setBadges(badgeResponses);
//            studyRecordDetailList.add(studyRecordDetail);
//
//            list.add(studyRecord);
//        }
//
//        Long userId = 1L;
//        studyRecordGetResponses.add(studyRecordGetResponse);
//
//        when(studyRecordRepository.findByMember_Id(userId, sort)).thenReturn(list);
//
//        List<StudyRecordGetResponse> studyRecordGetResponses1 = studyRecordCrudService.getStudyRecordByUserId(userId);
//        assertEquals(studyRecordGetResponses1.size(), studyRecordGetResponses.size());
//        assertEquals(studyRecordGetResponses1.get(0).getSubCategories().get(0).getStudyRecordDeatilList().size()
//                , studyRecordGetResponses.get(0).getSubCategories().get(0).getStudyRecordDeatilList().size());

    }
}