package com.testcode.yjp.last.domain.dto;

import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardUpdateRequestDto {

    private String title;
    private String content;
    private String uuid;
    private String imgName;
    private String fileName;

    @Builder
    public BoardUpdateRequestDto(String title, String content,String uuid, String imgName, String fileName){
        this.title = title;
        this.content = content;
        this.uuid = uuid;
        this.imgName = imgName;
        this.fileName = fileName;
    }

    public BoardUpdateRequestDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
    }
}
