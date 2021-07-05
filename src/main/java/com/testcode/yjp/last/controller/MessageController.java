package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.vo.Room;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Log4j2
public class MessageController {


    List<Room> roomList = new ArrayList<Room>();
    static int roomNumber = 0;

    @RequestMapping("/ptChat")
    public ModelAndView ptChat() {
//        Long member_id, Long trainer_id
//        log.info("member_id = " + member_id);
//        log.info("trainer_id = " + trainer_id);

        ModelAndView mv = new ModelAndView();
//        mv.addObject("member_id" , member_id);
//        mv.addObject("trainer_id", trainer_id);
        mv.setViewName("chat/ptChat");
        return mv;
    }

    @RequestMapping("/chat")
    public ModelAndView chat() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("chat/chat");
        return mv;
    }

    /**
     * 방 페이지
     *
     * @return
     */
    @RequestMapping("/room")
    public ModelAndView room() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("chat/room");
        return mv;
    }

    /**
     * 방 생성하기
     *
     * @param params
     * @return
     */
    @RequestMapping("/createRoom")
    public @ResponseBody
    List<Room> createRoom(@RequestParam HashMap<Object, Object> params) {
        String roomName = (String) params.get("roomName");
        if (roomName != null && !roomName.trim().equals("")) {
            Room room = new Room();
            room.setRoomNumber(++roomNumber);
            room.setRoomName(roomName);
            roomList.add(room);
        }
        return roomList;
    }

    /**
     * 방 정보가져오기
     *
     * @param params
     * @return
     */
    @RequestMapping("/getRoom")
    public @ResponseBody
    List<Room> getRoom(@RequestParam HashMap<Object, Object> params) {
        return roomList;
    }

    /**
     * 채팅방
     *
     * @return
     */
    @RequestMapping("/moveChating")
    public ModelAndView chating(@RequestParam HashMap<Object, Object> params) {
        ModelAndView mv = new ModelAndView();
        int roomNumber = Integer.parseInt((String) params.get("roomNumber"));

        List<Room> new_list = roomList.stream().filter(o -> o.getRoomNumber() == roomNumber).collect(Collectors.toList());
        if (new_list != null && new_list.size() > 0) {
            mv.addObject("roomName", params.get("roomName"));
            mv.addObject("roomNumber", params.get("roomNumber"));
            mv.setViewName("chat/chat");
        } else {
            mv.setViewName("chat/room");
        }
        return mv;
    }
}
