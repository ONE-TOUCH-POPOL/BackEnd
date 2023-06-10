package com.onepopol.studyrecord;

import com.onepopol.studyrecord.dto.StudyRecordCreate;
import com.onepopol.studyrecord.service.StudyRecordCrudService;
import com.onepopol.utils.ApiResult;
import com.onepopol.utils.Apiutils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/study-record")
public class StudyRecordController {
    private final StudyRecordCrudService studyRecordCrudService;

    @ResponseBody
    @PostMapping("/create")
    public ApiResult<?> studyRecordAdd(@RequestBody StudyRecordCreate studyRecordCreate) {
        Long result = studyRecordCrudService.addStudyRecord(studyRecordCreate);
        if (result == 1L) {
            return Apiutils.success("학습 기록 작성 성공");
        }
        return Apiutils.error("작성 실패", 1000);
    }
}
