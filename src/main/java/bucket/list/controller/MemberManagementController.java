package bucket.list.controller;

import bucket.list.domain.Member;
import bucket.list.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/management")
public class MemberManagementController {

    private final MemberService memberService;


    @GetMapping
    private String managementForm(){

        return "members/management";
    }

    @PostMapping("/memberNameSearch")
    private String management(@RequestParam String memberKeyword, Model model){
        long start = System.currentTimeMillis();

        List<Member> memberList = memberService.findMemberList(memberKeyword);
        long end = System.currentTimeMillis();

        log.info("소요시간 : {}",end-start);
        model.addAttribute("memberList", memberList);

        return "members/management";
    }

}
