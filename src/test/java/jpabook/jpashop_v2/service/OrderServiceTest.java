package jpabook.jpashop_v2.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop_v2.domain.Address;
import jpabook.jpashop_v2.domain.Member;
import jpabook.jpashop_v2.domain.Order;
import jpabook.jpashop_v2.domain.OrderStatus;
import jpabook.jpashop_v2.domain.item.Book;
import jpabook.jpashop_v2.domain.item.Item;
import jpabook.jpashop_v2.exception.NotEnoughStockException;
import jpabook.jpashop_v2.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest { //통합테스트 (단위 테스트의 경우 JPA 연결 없이 가볍게 가야함)

    @PersistenceContext
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    //주문 로직
    @Test
    public void OrderItem() throws Exception{

        //Given
        Member member=createMember();
        Item item=createBook("jpa",100000,10);
        int orderCount=2;

        //When
        Long orderId=orderService.order(member.getId(), item.getId(), orderCount);

        //Then
        Order getOrder=orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER,getOrder.getStatus(),"상품 주문시 상태는 ORDER");
        assertEquals(1,getOrder.getOrderItems().size(),"주문한 상품 종류 수가 정확해야 한다");
        //계산 로직 검증
        assertEquals(100000*2,getOrder.getTotalPrice(),"주문 가격은 가격*수량이다");
        assertEquals(8,item.getStockQuantity(),"주문 수량만틈 재고가 줄어야 한다");
    }

    @Test(expected = NotEnoughStockException.class)
    public void orderedItemExceed() throws Exception{
        //Given
        Member member=createMember();
        Item item=createBook("jpa",10000,10);

        int orderCount=11;

        //When
        orderService.order(member.getId(),item.getId(),orderCount);

        //Then
        fail("재고 수량 부족 예외가 발생해야 한다");
    }

    @Test
    public void cancelOrder(){

        //Given
        Member member=createMember();
        Item item=createBook("jpa",10000,10);
        int orderCount=2;

        Long orderId=orderService.order(member.getId(), item.getId(), orderCount);

        //When
        orderService.cancelOrder(orderId);

        //Then
        Order getOrder=orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL,getOrder.getStatus(),"주문 취소시 상태는 CANCEL 이다");
        assertEquals(10,item.getStockQuantity(),"주문이 취소된 상품은 그만큼 재고가 증가해야 한다");
    }

    private Member createMember(){
        Member member=new Member();
        member.setName("회원 1");
        member.setAddress(new Address("Seoul","Olympic","123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name,int price,int stockQuantity){
        Book book=new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

}