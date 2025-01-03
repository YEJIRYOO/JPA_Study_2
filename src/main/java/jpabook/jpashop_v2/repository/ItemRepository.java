package jpabook.jpashop_v2.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop_v2.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    //item 저장: id 이미 존재 시 업데이트(merge)
    public void save(Item item){
        if(item.getId()==null){
            em.persist(item);
        }else{
            em.merge(item);
        }
        /*
        식별자 X -> 새로운 엔티티로 판단하여 영속화
        식별자 O -> 병합 수행
        준영속 상태인 상품 엔티티 수정 시에는 id 있으므로 병합 수행
         */
    }

    public Item findOne(Long id){
        return em.find(Item.class,id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i",Item.class)
                .getResultList();
    }
}
