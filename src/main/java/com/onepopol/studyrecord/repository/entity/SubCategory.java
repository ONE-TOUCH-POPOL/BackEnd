package com.onepopol.studyrecord.repository.entity;

import com.onepopol.utils.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sub_category")
public class SubCategory extends BaseEntity {
    @Id
    @Column(name = "sub_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "main_code")
    private MainCategory mainCategory;

    private String code_name;

    @Builder
    public SubCategory(String code_name, MainCategory mainCategory) {
        this.code_name = code_name;
        this.mainCategory = mainCategory;
    }
}
