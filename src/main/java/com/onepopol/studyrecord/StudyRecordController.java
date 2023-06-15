package com.onepopol.studyrecord;

import com.onepopol.config.ValidationException;
import com.onepopol.member.repository.entity.Member;
import com.onepopol.member.security.MemberDetailsImpl;
import com.onepopol.studyrecord.dto.MainCategoryCreate;
import com.onepopol.studyrecord.dto.StudyRecordCreate;
import com.onepopol.studyrecord.dto.StudyRecordGetResponse;
import com.onepopol.studyrecord.service.StudyRecordCategoryService;
import com.onepopol.studyrecord.service.StudyRecordCrudService;
import com.onepopol.utils.ApiResult;
import com.onepopol.utils.Apiutils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/study-record")
@CrossOrigin
public class StudyRecordController {
    private final StudyRecordCrudService studyRecordCrudService;
    private final StudyRecordCategoryService studyRecordCategoryService;

    @ResponseBody
    @PostMapping("/create")
    public ApiResult<?> studyRecordAdd(@Valid @RequestBody StudyRecordCreate studyRecordCreate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 처리
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            throw new ValidationException(fieldErrors);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberDetailsImpl userDetails = (MemberDetailsImpl) authentication.getPrincipal();
        Member member = new Member().builder()
                .id(userDetails.getUserId())
                .build();
        Long result = studyRecordCrudService.addStudyRecord(studyRecordCreate);
        return Apiutils.success("학습 기록 작성 성공");
    }

    @ResponseBody
    @GetMapping
    public ApiResult<?> testUserInfo(Principal principal) {
        return Apiutils.success(principal);
    }

    @ResponseBody
    @GetMapping("/all")
    public ApiResult<?> studyRecordGetAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberDetailsImpl userDetails = (MemberDetailsImpl) authentication.getPrincipal();
        List<StudyRecordGetResponse> res = studyRecordCrudService.getStudyRecordByUserId(userDetails.getUserId());
        return Apiutils.success(res);
    }

    @ResponseBody
    @PostMapping("/main-category")
    public ApiResult<?> mainCategoryAdd(@RequestBody MainCategoryCreate mainCategoryCreate, Principal principal) {
        Long res = studyRecordCategoryService.addMainCategory(mainCategoryCreate);
        return Apiutils.success("메인 카테고리 생성에 성공했습니다.");
    }

}
