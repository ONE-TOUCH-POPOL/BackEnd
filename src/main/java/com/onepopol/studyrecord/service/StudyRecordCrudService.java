package com.onepopol.studyrecord.service;


import com.onepopol.studyrecord.dto.StudyRecordCreate;
import com.onepopol.studyrecord.dto.StudyRecordGetResponse;
import com.onepopol.studyrecord.repository.StudyRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyRecordCrudService {
    private final StudyRecordRepository studyRecordRepository;

    @Transactional
    public Long addStudyRecord(StudyRecordCreate studyRecordCreate) {
        return studyRecordRepository.save(studyRecordCreate.toEntity()).getId();
    }

    @Transactional
    public List<StudyRecordGetResponse> getStudyRecordByUserId(Long userId) {
        return studyRecordRepository.findAllWithMemberId(userId);
    }
}
