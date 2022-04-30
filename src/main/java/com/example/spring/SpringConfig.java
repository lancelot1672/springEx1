package com.example.spring;

import com.example.spring.member.repository.JdbcMemberRepository;
import com.example.spring.member.repository.JdbcTemplateMemberRepository;
import com.example.spring.member.repository.MemberRepository;
import com.example.spring.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    //자바 코드로 직접 Spring Bean 등록하기
    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {    //DataSource 생성자 주입
        this.dataSource = dataSource;
    }


    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository(){
        //return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
        return new JdbcTemplateMemberRepository(dataSource);
    }
}
