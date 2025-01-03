package jpabook.jpashop_v2.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop_v2.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;
    /*
    @PersistenceContext
    //스프링부트, @Autowired 로 대체 가능 -> Args 롬복 사용 가능
    private EntityManager em;
     */

    public long save(Member member){
        em.persist(member);
        return member.getId(); //commend 와 쿼리를 분리하자
    }

    public Member findOne(Long id){
        return em.find(Member.class,id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}

