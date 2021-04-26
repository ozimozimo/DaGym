package com.testcode.yjp.last.domain.dto.android;

import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AndBoardSaveDto {
    private Long id;
    private String title;
    private String user_id;
    private String content;
    private int hit;
    private Member member;

    public AndBoardSaveDto(Long id, String title, String user_id, String content, int hit, Member member) {
        this.id = id;
        this.title = title;
        this.user_id = user_id;
        this.content = content;
        this.hit = hit;
        this.member = member;
    }

    public Board toEntity(){
        return Board.builder()
                .title(title)
                .content(content)
                .user_id(user_id)
                .hit(hit)
                .member(member)
                .build();
    }
}
