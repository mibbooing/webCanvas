package com.example.websocketCanvas.Controller;

import com.example.websocketCanvas.vo.Room;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    List<Room> roomList = new ArrayList<Room>();
    static int roomNumber = 0;

    @RequestMapping("/chat")
    public String chat(Model model) {
        return "chat";
    }

    @RequestMapping("/room")
    public String room(Model model) {
        return "room";
    }

    @RequestMapping("/createRoom")
    public @ResponseBody List<Room> createRoom(@RequestParam HashMap<Object, Object> params) {
        String roomName = (String) params.get("roomName");
        if (roomName != null && !roomName.trim().equals("")) {
            Room room = new Room();
            room.setRoomNumber(++roomNumber);
            room.setRoomName(roomName);
            roomList.add(room);
        }
        return roomList;
    }

    @RequestMapping("/getRoom")
    public @ResponseBody List<Room> getRoom(@RequestParam HashMap<Object, Object> params) {
        return roomList;
    }

    @RequestMapping("/moveChating")
    public ModelAndView chating(@RequestParam HashMap<Object, Object> params) {
        ModelAndView mv = new ModelAndView();
        int roomNumber = Integer.parseInt((String) params.get("roomNumber"));

        List<Room> new_list = roomList.stream().filter(o -> o.getRoomNumber() == roomNumber).collect(Collectors.toList());
        if (new_list != null && new_list.size() > 0) {
            mv.addObject("roomName", params.get("roomName"));
            mv.addObject("roomNumber", params.get("roomNumber"));
            mv.setViewName("chat");
        } else {
            mv.setViewName("room");
        }
        return mv;
    }
}
