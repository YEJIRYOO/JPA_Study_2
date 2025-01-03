package jpabook.jpashop_v2.service;

import jpabook.jpashop_v2.domain.Member;
import jpabook.jpashop_v2.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional //트랜잭션 실행 후 테스트 끝나면 롤백
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    /*
    회원 가입 로직 테스트
     */
    @Test
    public void joinMember() throws Exception{ //포괄적 예외 처리

        //Given
        Member member = new Member();
        member.setName("ryoo");

        //When
        Long saveId= memberService.join(member);

        //Then
        //생성한 member와 Service롤 통해 저장된 member가 일치하는 지 확인
        Assertions.assertEquals(member,memberRepository.findOne(saveId));
    }

    /*
    죽복 회원 예외 처리 로직 테스트
     */
    @Test(expected = IllegalStateException.class)
    public void repeatedMemberException() throws Exception{
        //Given
        Member member1 = new Member();
        member1.setName("ryoo");

        Member member2 = new Member();
        member2.setName("ryoo");

        //When
        memberService.join(member1);
        memberService.join(member2);

        //Then
        Assert.fail("예외가 발생해야 한다.");
    }
}
