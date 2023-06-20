package com.onepopol.codeFormat;

import com.onepopol.codeFormat.dto.CodeFormatRequest;
import com.onepopol.codeFormat.service.CodeFormattingService;
import com.onepopol.config.BaseException;
import com.onepopol.config.ValidationException;
import com.onepopol.utils.ApiResult;
import com.onepopol.utils.Apiutils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/code-format")
@CrossOrigin
public class CodeFormatController {

    private final CodeFormattingService codeFormattingService;

    @PostMapping()
    public ApiResult<?> formatCode(@Valid @RequestBody CodeFormatRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 처리
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            throw new ValidationException(fieldErrors);
        }
        try {
            String language = request.getLanguage();
            String sourceCode = request.getSourceCode();

            String result = codeFormattingService.formatCode(language, sourceCode);

            return Apiutils.success(result);
        }catch(BaseException e){
            throw new BaseException(e.getApiError());
        }
    }

    // demo version
    @PostMapping("/js")
    public ApiResult<?> formatCodePrettier(@RequestBody CodeFormatRequest request) throws IOException {
        String language = request.getLanguage();
        String sourceCode = request.getSourceCode();

        String result = codeFormattingService.formatCodePrettier(language, sourceCode);

        return Apiutils.success(result);
    }
}
