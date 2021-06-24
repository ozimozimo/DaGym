package com.testcode.yjp.last.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class Room {

    // 방번호
    int roomNumber;
    //방이름
    String roomName;

    // 나중에 트레이너 아이디 , 회원 아이디 들어갈거임


    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", roomName='" + roomName + '\'' +
                '}';
    }
}
