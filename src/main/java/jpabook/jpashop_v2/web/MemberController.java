package jpabook.jpashop_v2.web;

import jakarta.validation.Valid;
import jpabook.jpashop_v2.domain.Address;
import jpabook.jpashop_v2.domain.Member;
import jpabook.jpashop_v2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //컨트롤러 -> view로 데이터 전달
    @GetMapping(value = "/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm",new MemberForm());
        return "members/createMemberForm"; //뷰의 이름 (해당 이름에 대응하는 HTML파일 템플릿 렌더링
    }

    @PostMapping(value = "/members/new")
    public String create(@Valid MemberForm form, BindingResult result){
        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        Address address=new Address(
                form.getCity(),form.getStreet(),form.getZipcode());
        Member member=new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);

        return "redirect:/";
    }

    /*
    회원 목록 조회
     */
    @GetMapping(value = "/members")
    public String list(Model model){
        List<Member> members=memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
