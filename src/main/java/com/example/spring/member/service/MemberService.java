package com.example.spring.member.service;

import com.example.spring.member.domain.Member;
import com.example.spring.member.repository.MemberRepository;
import com.example.spring.member.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//Spring이 서비스를 알고 bean으로 등록해줌.
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    //생성자 주입
    @Autowired //컨테이너에 있는 MemberRepository를 연결시켜줌
    //bean으로 등록되어 있지 않다면 실행이 안됌. 1. Service 2. 직접 Java 코드로 Config 파일 작성 (Bean 등록)
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
    * 회원가입
    *
    * */
    public Long join(Member member){
        // 같은 이름이 있는 중복 회원x
        validateDuplicationMember(member);


        memberRepository.save(member);
        return member.getId();
    }

    // 유효성 검사
    private void validateDuplicationMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
    /*
        전체 회원 조회

     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
