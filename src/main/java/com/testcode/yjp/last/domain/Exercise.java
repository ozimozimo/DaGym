package com.testcode.yjp.last.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false, exclude = {"exRecords"})
@Table(name = "exercise")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ex_id")
    private Long ex_id;
    private String ex_name;
    private String ex_category;
    private String ex_parts;

    @OneToMany(mappedBy = "exercise", orphanRemoval = true)
    private List<ExRecord> exRecords = new ArrayList<>();
}
