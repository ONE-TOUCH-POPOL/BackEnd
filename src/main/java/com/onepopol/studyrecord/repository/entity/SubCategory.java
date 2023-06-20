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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "main_code")
    private MainCategory mainCategory;

    private String codeName;

    @Builder
    public SubCategory(String codeName, MainCategory mainCategory) {
        this.codeName = codeName;
        this.mainCategory = mainCategory;
    }
}
