package com.onepopol.converter.service;

import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@RequiredArgsConstructor
public class ConverterService {
    public String doOcr(MultipartFile image){
        try {

            String originFileName = image.getOriginalFilename();
            String extension = null;
            int dotIndex = originFileName.lastIndexOf('.');
            if (dotIndex > 0 && dotIndex < originFileName.length() - 1) {
                extension = originFileName.substring(dotIndex + 1);
            }
            File tempFile = File.createTempFile("ocr_image", "."+extension);
            image.transferTo(tempFile);

            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("src/main/resources/tessdata");
            tesseract.setLanguage("eng+osd+kor");

            String imageText=tesseract.doOCR(tempFile);

            return imageText;

        }catch (TesseractException e){
            e.printStackTrace();
            System.out.println("Tesseract Error");
        } catch(Exception e){
            e.printStackTrace();
        }
        return "실패";
    }
}
/* 해상도 200DPI 이상
 * 일반적으로 300DPI
 * 1bpp 흑백 또는 8bpp 회색조 비압축 TIFF
 *
 */
