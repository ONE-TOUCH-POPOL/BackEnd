package com.onepopol.studyrecord.repository;

import com.onepopol.studyrecord.repository.entity.StudyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyRecordRepository extends JpaRepository<StudyRecord, Long> {
    List<StudyRecord> findByMember_Id(Long memberId);
}
