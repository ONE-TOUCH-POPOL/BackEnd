package com.onepopol.studyrecord.repository;

import com.onepopol.studyrecord.repository.entity.StudyRecord;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyRecordRepository extends JpaRepository<StudyRecord, Long> {
    @EntityGraph(attributePaths = {"subCategory", "subCategory.mainCategory", "badges", "badges.badgeCategory"})
    List<StudyRecord> findByMember_Id(Long memberId, Sort sort);

}
