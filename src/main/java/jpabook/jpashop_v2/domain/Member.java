package jpabook.jpashop_v2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter //setter 가급적 자제
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id") //일관성 필요
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")//연관관계 거울 표현 (Order 테이블-member 필드 의해 매핑)
    private List<Order> orders=new ArrayList<>();
}
