package com.onepopol.studyrecord;

import com.onepopol.config.ValidationException;
import com.onepopol.studyrecord.dto.StudyRecordCreate;
import com.onepopol.studyrecord.service.StudyRecordCrudService;
import com.onepopol.utils.ApiResult;
import com.onepopol.utils.Apiutils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/study-record")
public class StudyRecordController {
    private final StudyRecordCrudService studyRecordCrudService;

    @ResponseBody
    @PostMapping("/create")
    public ApiResult<?> studyRecordAdd(@Valid @RequestBody StudyRecordCreate studyRecordCreate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 처리
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            throw new ValidationException(fieldErrors);
        }
        Long result = studyRecordCrudService.addStudyRecord(studyRecordCreate);
        return Apiutils.success("학습 기록 작성 성공");
    }
}
