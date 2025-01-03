package jpabook.jpashop_v2.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity //필요성? 상속 관계 의해 자동으로 빈에 등록되지 않나?
@Getter @Setter
@DiscriminatorValue("B")
public class Book extends Item{

    private String author;
    private String isbn;
}
