package com.onepopol.codeFormat.service;

import com.google.googlejavaformat.java.Formatter;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class CodeFormattingService {

    public String formatCode(String language, String sourceCode) {
        try {
            Formatter formatter = new Formatter();
            String formattedCode = formatter.formatSource(sourceCode);

            return new String(formattedCode);
        } catch (Exception e) {
            // 포맷팅 및 정렬 실패 시 처리
            e.printStackTrace();
            return "실패";
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
