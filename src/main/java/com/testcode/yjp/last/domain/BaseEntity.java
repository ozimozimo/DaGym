package com.testcode.yjp.last.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.testcode.yjp.last.domain.dto.TrReviewDto;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@Getter
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
abstract public class BaseEntity {
    @CreationTimestamp
    @Column(name = "regdate",updatable = false)
    private LocalDateTime regDate;

    @UpdateTimestamp
    @Column(name = "moddate")
    private LocalDateTime modDate;


}
