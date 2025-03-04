package jpabook.jpashop_v2;

import jakarta.persistence.EntityManager;
import jpabook.jpashop_v2.domain.Delivery;
import jpabook.jpashop_v2.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {
//    private final InitService initService;

    //PostConstruct

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final EntityManager em;

        public void dbInit1(){

        }

        public void dbInit2(){

        }

        private Member createMember(String name,String city,String street,String zipcode){

        }

        private Delivery createDelivery(Member member){
            Delivery delivery=new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

    }
}
