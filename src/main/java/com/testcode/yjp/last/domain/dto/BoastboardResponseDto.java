package com.testcode.yjp.last.domain.dto;

import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.Boastboard;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoastboardResponseDto {

    private Long id;
    private String title;
    private String user_id;
    private String content;
    private int hit;
    private LocalDateTime modifiedDate;

    public BoastboardResponseDto(Boastboard entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.user_id = entity.getUser_id();
        this.hit = entity.getHit();
        this.modifiedDate = entity.getModDate();
    }
}
