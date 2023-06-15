package com.onepopol.studyrecord.repository.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "main_category")
public class MainCategory {
    @Id
    @Column(name = "main_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code_name;

    @Builder
    public MainCategory(String code_name) {
        this.code_name = code_name;
    }
}
