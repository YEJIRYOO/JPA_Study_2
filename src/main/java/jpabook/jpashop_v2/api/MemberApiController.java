package jpabook.jpashop_v2.api;

import jakarta.validation.Valid;
import jpabook.jpashop_v2.domain.Member;
import jpabook.jpashop_v2.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /*
     * 등록 V1: 요청 값으로 Member 엔티티 직접 받음
     */

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id=memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data
    static class CreateMemberResponse{
        private Long id;

        private CreateMemberResponse(Long id){
            this.id=id;
        }
    }

    /*
    수정 API
     */
    @PutMapping("/api/v2/members/{id}")
}
