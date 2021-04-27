package com.testcode.yjp.last.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;

    private String uuid;

    private String imgName;

//    @ManyToOne
//    @JoinColumn(name = "hb_num")
//    private Board board;
}
