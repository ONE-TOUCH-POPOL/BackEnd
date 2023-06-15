package com.onepopol.studyrecord.repository;

import com.onepopol.studyrecord.dto.StudyRecordGetResponse;
import com.onepopol.studyrecord.repository.entity.StudyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudyRecordRepository extends JpaRepository<StudyRecord, Long> {
    @Query("SELECT new com.onepopol.studyrecord.dto.StudyRecordGetResponse(sr.id, sr.title, sr.content, sr.category, sr.recordDate, sr.member.id) FROM StudyRecord sr WHERE sr.member.id = :memberId")
    List<StudyRecordGetResponse> findAllWithMemberId(@Param("memberId") Long memberId);
}
