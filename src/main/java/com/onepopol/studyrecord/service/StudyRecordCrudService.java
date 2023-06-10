package com.onepopol.studyrecord.service;


import com.onepopol.studyrecord.dto.StudyRecordCreate;
import com.onepopol.studyrecord.repository.StudyRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyRecordCrudService {
    private final StudyRecordRepository studyRecordRepository;

    public Long addStudyRecord(StudyRecordCreate studyRecordCreate) {
        return studyRecordRepository.save(studyRecordCreate.toEntity()).getId();
    }
}
