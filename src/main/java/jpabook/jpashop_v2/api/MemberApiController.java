package jpabook.jpashop_v2.api;

import jakarta.validation.Valid;
import jpabook.jpashop_v2.domain.Member;
import jpabook.jpashop_v2.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

//RestController: JSON 형태로 데이터 반환
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /*
     * 등록 V1: 요청 값으로 Member 엔티티 직접 받음
     */

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        private CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    /*
     * 등록 V2: 요청 값으로 Member 엔티티 대신 별도의 DTO 받는다
     */
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid
                                             CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberRequest {
        private String name;
    }


    /*
     * =============================================
     * 회원 수정 API
     */
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest {
        public String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    /*
     * =============================================
     * 조회 V1: 응답 값으로 엔티티 직접 외부 노출
     */

    @GetMapping("/api/v1/members")
    public List<Member> membersV1(){
        return memberService.findMembers();
    }

    /*
     * 조회 V2: 응답 값으로 엔티티 아닌 별도의 DTO 반환
     */
    @GetMapping("/api/v2/members")
    public Result membersV2(){
        List<Member> findMembers=memberService.findMembers();
        //엔티티 -> DTO 변환
        List<MemberDto> collect=findMembers.stream()
                .map(m->new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }
}
