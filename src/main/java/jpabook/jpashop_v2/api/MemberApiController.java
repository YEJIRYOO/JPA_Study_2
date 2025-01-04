package jpabook.jpashop_v2.api;

import jakarta.validation.Valid;
import jpabook.jpashop_v2.domain.Member;
import jpabook.jpashop_v2.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /*
     * 등록 V2
     */

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(
            @RequestBody @Valid Member member
            ){
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
}
