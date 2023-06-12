package com.onepopol.utils;

import com.onepopol.constant.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "VARCHAR(20) DEFAULT 'ACTIVE'")
    private Status status = Status.ACTIVE;

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
        this.status = Status.ACTIVE;
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }
}
