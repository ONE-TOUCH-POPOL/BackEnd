package com.onepopol.codeFormat.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeFormatRequest {
    @NotBlank
    String language;
    @NotBlank
    String sourceCode;
}