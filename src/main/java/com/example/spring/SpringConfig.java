package com.example.spring;

import com.example.spring.member.repository.MemberRepository;
import com.example.spring.member.repository.MemoryMemberRepository;
import com.example.spring.member.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    //자바 코드로 직접 Spring Bean 등록하기
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
