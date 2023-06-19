package com.onepopol.codeFormat;

import com.onepopol.codeFormat.dto.CodeFormatRequest;
import com.onepopol.codeFormat.service.CodeFormattingService;
import com.onepopol.utils.ApiResult;
import com.onepopol.utils.Apiutils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/codeFormat")
@CrossOrigin
public class CodeFormatController {

    private final CodeFormattingService codeFormattingService;

    @PostMapping()
    public ApiResult<?> formatCode(@RequestBody CodeFormatRequest request) throws IOException {
        String language = request.getLanguage();
        String sourceCode = request.getSourceCode();

        String result = codeFormattingService.formatCode(language,sourceCode);

        return Apiutils.success(result);
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
