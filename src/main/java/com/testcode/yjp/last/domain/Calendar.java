package com.testcode.yjp.last.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "calendar_record")
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "calendar_id")
    private Long id;

    @Column(name = "calendar_title")
    private String title;
    @Column(name = "calendar_start")
    private String start;
    @Column(name = "calendar_end")
    private String end;
    @Column(name = "calendar_description")
    private String description;
    @Column(name = "calendar_type")
    private String type;
    @Column(name = "calendar_backgroundColor")
    private String backgroundColor;
    @Column(name = "calendar_textColor")
    private String textColor;
//    @Column(name = "calendar_allDay")
//    private boolean allDay;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private TrainerInfo trainerInfo;

    public void ChangeTime(String start, String end) {
        this.start =start;
        this.end = end;
    }

    public void ResizeTime(String start, String end) {
        this.start =start;
        this.end = end;
    }

    public void update(String title, String start, String end,
                       String description, String type, String backgroundColor,
                       String textColor) {
        this.title =title;
        this.start =start;
        this.end = end;
        this.description=description;
        this.type =type;
        this.backgroundColor=backgroundColor;
        this.textColor = textColor;
    }
}
