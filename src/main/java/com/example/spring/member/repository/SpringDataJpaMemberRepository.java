package com.example.spring.member.repository;

import com.example.spring.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//interface 가 interface를 받을 때에는 extends 사용
//interface 는 다중 상속 허용
// 스프링 데이터 JPA가 SpringDataJpaMemberRepository를 스프링 빈으로 자동 등록해줌.
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name);
}
