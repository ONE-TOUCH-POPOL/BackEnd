package com.onepopol.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberController {

    @Autowired
    MemberService memberService;

    private static final int SUCCESS = 1;

    @PostMapping("/join")
    public ResponseEntity joinMember(Member member){
        int registerResult = memberService.registerMember(member);
        Map<String, String> map = new HashMap<>();

        if(registerResult == SUCCESS){
            map.put("result", "success");
            return ResponseEntity.ok().body(map);
        }else {
            map.put("result", "fail");
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.NOT_FOUND);
        }
    }
}
