package com.testcode.yjp.last.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import groovy.util.logging.Log4j;
import groovy.util.logging.Log4j2;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@EqualsAndHashCode(callSuper = false, exclude = {"boardImages"}) //"emotion",
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Table(name = "boast_board")
public class Boastboard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "bb_num")
    private Long id;

    @Column(name = "bb_title")
    private String title;

    @Column(name = "user_id")
    private String user_id;

    @Column(name = "bb_content")
    private String content;

    @Column(name = "bb_hit")
    private int hit;

    // 트레이너 이미지 변환
    private String uuid;

    // 트레이너 이미지
    private String imgName;

    // 파일 이름
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void update(String title, String content,String uuid, String imgName, String fileName ){
        this.title = title;
        this.content = content;
        this.uuid = uuid;
        this.imgName = imgName;
        this.fileName = fileName;
    }
}
