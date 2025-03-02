package jpabook.jpashop_v2.service;

import jpabook.jpashop_v2.domain.Member;
import jpabook.jpashop_v2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    //생성자 주
    private final MemberRepository memberRepository;

    /*
    =======회원 가입=======
     */
    @Transactional //변경 허용_ readOnly=false
    public Long join(Member member){
        validateDuplicateMember(member); //중복 회원 검증
        //영속성 pk는 id
        memberRepository.save(member); //레포지토리 저장
        return member.getId();
    }

    //중복 기준: 회원 이름
    public void validateDuplicateMember(Member member){
        List<Member> findMembers=
                memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /*
     * 회원 수정 - 이름 수정
     */
    @Transactional
    public void update(Long id,String name){
        Member member=memberRepository.findOne(id);
        member.setName(name);
    }

    /*
    =======전체 회원 조회=======
    */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}

