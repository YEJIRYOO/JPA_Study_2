package jpabook.jpashop_v2.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop_v2.domain.Category;
import jpabook.jpashop_v2.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype") //discriminatorValue로 받음_ Single Table 내에서 구분용
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories=new ArrayList<Category>();

    /*
    =====비즈니스 로직=====
     */
    public void addStock(int quantity){
        this.stockQuantity+=quantity;
    }

    //재고 부족 시 예외 발생
    public void removeStock(int quantity){
        int restStock=this.stockQuantity-quantity;
        if(restStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity=restStock;
    }
}
