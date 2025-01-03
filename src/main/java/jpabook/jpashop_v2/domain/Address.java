package jpabook.jpashop_v2.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

//값 타입은 변경 불가능하게 설계해야함
@Embeddable //JPA 내장 타입
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    //내장 타입의 경우 기본 생성자 필요 (jpa 리플랙션 등에 필요)
    protected Address(){
    }

    public Address(String city,String street,String zipcode){
        this.city=city;
        this.street=street;
        this.zipcode=zipcode;
    }
}
