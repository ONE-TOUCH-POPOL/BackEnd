package com.onepopol.converter;

import com.onepopol.config.BaseException;
import com.onepopol.converter.service.ConverterService;
import com.onepopol.utils.ApiError;
import com.onepopol.utils.ApiResult;
import com.onepopol.utils.Apiutils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/convert")
@CrossOrigin
public class ConverterController {

    private final ConverterService converterService;

    @PostMapping("/image")
    public ApiResult<String> convertImageToText(@RequestBody MultipartFile image) {
        try {
            String resultText = converterService.doOcr(image);

            return Apiutils.success(resultText);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(new ApiError("실패", 2000));
        }
    }
}
