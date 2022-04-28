package com.example.spring.repository;

import com.example.spring.member.domain.Member;
import com.example.spring.member.repository.MemberRepository;
import com.example.spring.member.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach(){
        //단위 테스트가 끝나면 저장소, 공용 데이터를 지워줘야함.
        memberRepository.clearStore();
    }

    @Test
    public void save(){
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        memberRepository.save(member);

        //after
        Member result = memberRepository.findById(member.getId()).get();

        //Assertions
        //Assertions.assertEquals(member, result);

        assertThat(member).isEqualTo(result);
    }
    @Test
    public void findByName(){
        Member member = new Member();
        member.setName("spring1");
        memberRepository.save(member);

        Member member2 = new Member();
        member2.setName("spring2");
        memberRepository.save(member2);

        Member result1 = memberRepository.findByName("spring1").get();

        assertThat(result1).isEqualTo(member);
    }
    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        memberRepository.save(member2);

        List<Member> result = memberRepository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}
