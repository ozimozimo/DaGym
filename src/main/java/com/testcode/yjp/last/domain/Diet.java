package com.testcode.yjp.last.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Diet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "diet_id")
    private Long diet_id;
    private String diet_member_id;
    private String diet_name;
    private String eat_rate;
    private String diet_kcal;
    private String diet_carbo;
    private String diet_protein;
    private String diet_fat;
    private String diet_time;
    private String diet_date;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Diet(Long diet_id, String diet_member_id, String diet_name, String diet_kcal, String diet_carbo, String diet_protein, String diet_fat, String diet_time, String diet_date, String eat_rate) {
        this.diet_id = diet_id;
        this.diet_member_id = diet_member_id;
        this.diet_name = diet_name;
        this.diet_kcal = diet_kcal;
        this.diet_carbo = diet_carbo;
        this.diet_protein = diet_protein;
        this.diet_fat = diet_fat;
        this.diet_time = diet_time;
        this.diet_date = diet_date;
        this.eat_rate = eat_rate;
    }
}
