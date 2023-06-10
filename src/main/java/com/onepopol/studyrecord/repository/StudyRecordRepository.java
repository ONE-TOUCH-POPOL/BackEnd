package com.onepopol.studyrecord.repository;

import com.onepopol.studyrecord.repository.entity.StudyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRecordRepository extends JpaRepository<StudyRecord, Long> {
}
