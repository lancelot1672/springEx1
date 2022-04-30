package com.example.spring.member.service;

import com.example.spring.member.domain.Member;
import com.example.spring.member.repository.MemberRepository;
import com.example.spring.member.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    /*
        - Test는 한글로 적어도 됨
        @SpringBootTest
        - 스프링 컨테이너와 테스트를 함께 실행한다.

        @Transactional
        - 테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후에 항상 롤백한다.
        - 테스트 끝나면 commit 하지 않고 Rollback 시켜버림
     */

    //Test는 필드 주입으로 해도 됨.
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findById(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

       /* try{
            memberService.join(member2);
            fail();
        }catch(IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/
    }

    @Test
    void 회원이름으로_회원찾기() {
        //given
        Member member1 = new Member();
        member1.setName("spring");
        memberService.join(member1);

        //when - 회원 찾았을 때
        Member member2 = memberService.findByName(member1.getName()).get();

        //then
        assertThat(member1).isEqualTo(member2);
    }
    @Test
    void 회원아이디로_회원찾기() {
        //given
        Member member1 = new Member();
        member1.setName("spring");
        memberService.join(member1);

        //when - 회원 찾았을 때
        Member result = memberService.findById(member1.getId()).get();

        //then
        assertThat(member1).isEqualTo(result);

    }
}