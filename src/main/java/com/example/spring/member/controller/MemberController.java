package com.example.spring.member.controller;

import com.example.spring.member.domain.Member;
import com.example.spring.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String join(Member member){
        Member m = new Member();

        m.setName(member.getName());

        memberService.join(m);

        return "redirect:/";
    }
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }


}
