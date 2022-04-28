package com.example.spring.member.controller;

import com.example.spring.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

// Controller, Service, Repository 는 기본적으로 @Component 어노테이션이 들어가있음
@Controller
public class MemberController {
    //스프링이 관리를 함

    private final MemberService memberService;


    //Di (생성자 주입)
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
