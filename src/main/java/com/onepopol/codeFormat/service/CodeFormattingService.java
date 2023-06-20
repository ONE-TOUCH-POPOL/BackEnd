package com.onepopol.codeFormat.service;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import com.onepopol.config.BaseException;
import com.onepopol.utils.ApiError;
import org.springframework.stereotype.Service;

import java.io.*;

import static com.onepopol.codeFormat.error.CodeFormatErrorCode.FAIL_FORMATTING;
import static com.onepopol.codeFormat.error.CodeFormatErrorCode.UNSUPPORTED_LANGUAGE;

@Service
public class CodeFormattingService {

    public String formatCode(String language, String sourceCode) {
        try {
            switch(language){
                case "java":{
                    return formatJava(sourceCode);
                }
                default: {
                    throw new BaseException(new ApiError(UNSUPPORTED_LANGUAGE.getMessage(), UNSUPPORTED_LANGUAGE.getCode()));
                }
            }
        } catch (BaseException e) {
            throw new BaseException(e.getApiError());
        }
    }
    private String formatJava(String sourceCode){
        try{
            Formatter formatter = new Formatter();
            String formattedCode = formatter.formatSource(sourceCode);

            return new String(formattedCode);
        }catch(FormatterException e){
            throw new BaseException(new ApiError(FAIL_FORMATTING.getMessage(), FAIL_FORMATTING.getCode()));
        }
    }

    public String formatCodePrettier(String language, String sourceCode) throws IOException {
        // npx 경로 및 명령어 설정
        String command = "C:\\Program Files\\nodejs\\npx.cmd prettier --parser " + language + " --write";

        // Prettier 명령 실행
        Process process = Runtime.getRuntime().exec(command);
        OutputStream outputStream = process.getOutputStream();

        // 코드 문자열 전달
        PrintWriter writer = new PrintWriter(outputStream);
        writer.println(sourceCode);
        writer.close();

        // 포맷팅된 결과 읽기
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder formattedCode = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            formattedCode.append(line).append("\n");
        }
        reader.close();

        return formattedCode.toString();
    }
}
