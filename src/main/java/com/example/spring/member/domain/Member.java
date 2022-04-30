package com.example.spring.member.domain;

import javax.persistence.*;

@Entity
public class Member {
    // DB가 알아서 생성해주는 것을 Identity 라고 함.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
