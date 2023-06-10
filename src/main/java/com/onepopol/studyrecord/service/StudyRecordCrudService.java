package com.onepopol.studyrecord.service;


import com.onepopol.studyrecord.dto.StudyRecordCreate;
import com.onepopol.studyrecord.dto.StudyRecordGetResponse;
import com.onepopol.studyrecord.repository.StudyRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyRecordCrudService {
    private final StudyRecordRepository studyRecordRepository;

    public Long addStudyRecord(StudyRecordCreate studyRecordCreate) {
        return studyRecordRepository.save(studyRecordCreate.toEntity()).getId();
    }

    public List<StudyRecordGetResponse> getStudyRecordByUserId(Long userId) {
        return studyRecordRepository.findByUserId(userId)
                .stream()
                .map(StudyRecordGetResponse::new)
                .collect(Collectors.toList());
    }
}
