package bucket.list.controller;

import bucket.list.config.LoginUser;
import bucket.list.domain.Community;
import bucket.list.domain.Member;
import bucket.list.domain.Participation;
import bucket.list.memberdto.MailDto;
import bucket.list.memberdto.MemberFormDto;
import bucket.list.memberdto.SessionMember;
import bucket.list.memberdto.UpdatePasswordDto;
import bucket.list.service.Community.CommunityService;
import bucket.list.service.Member.MemberService;
import bucket.list.service.Participation.ParticipationService;

import bucket.list.service.common.SendMailService;
import bucket.list.validator.MemberValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;



import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final ParticipationService participationService;
    private final CommunityService communityService;
    private final MemberValidator validator;
    private final SendMailService sendMailService;

    @GetMapping("/create")
    public String createForm(Model model){
        model.addAttribute("member", new MemberFormDto());

        return "members/create";

    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("member") MemberFormDto memberFormDto, BindingResult bindingResult,Model model){

        if(bindingResult.hasErrors()){
            return "members/create";
        }
            try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);

        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "members/create";
        }

        return "members/create_success";
    }

    @GetMapping("/login")
    public String login(){
        return "members/login";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");

        return "members/login";
    }

    @GetMapping("/findPassword")
    public String findPassword(){

        return "members/findPassword";
    }

    @PostMapping("/findPassword")
    public String sendMail(@RequestParam String memberEmail,Model model){

        try {
            memberService.equalEmail(memberEmail);
            MailDto mailDto = memberService.createOrChangePassword(memberEmail);
            sendMailService.mailSend(mailDto);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "members/findPassword";
        }

        return "members/sendMailSuccess";
    }

    @GetMapping("/mypage")
    public String myPage(@LoginUser SessionMember sessionMember, @PageableDefault(page = 0, size = 4,
            sort = "participationIdx",direction = Sort.Direction.DESC) @Qualifier("participation") Pageable pageableParticipation, @PageableDefault(page = 0, size = 4,
            sort = "communityIdx",direction = Sort.Direction.DESC) @Qualifier("community") Pageable pageableCommunity, Model model){

        Page<Participation> participation = participationService.findParticipationList(sessionMember.getMemberId(), pageableParticipation);
        Page<Community> community = communityService.findCommunityList(sessionMember.getMemberId(), pageableCommunity);


        //현재 페이지 변수 Pageable 0페이지부터 시작하기 +1을해줘서 1페이지부터 반영한다
        int nowPage = participation.getPageable().getPageNumber() + 1;
        //블럭에서 보여줄 시작페이지(Math.max 한이유는 시작페이지가 마이너스 값일 수는 업으니깐 Math.max를 사용)
        int startPage =Math.max(nowPage-4,1) ;
        //블럭에서 보여줄때 마지막페이지(Math.min 한이유는 총페이지가 10페이지인데, 현재페이지가 9페이지이면 14페이지가되므로 오류,
        //그렇기에 getTotalpage를  min으로설정)
        int endPage = Math.min(nowPage + 5, participation.getTotalPages());

        myPageCommunityPaging(model, community, nowPage, startPage, endPage);
        myPageParticipationPaging(model, participation, nowPage, startPage, endPage);


        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        model.addAttribute("participation",participation);
        model.addAttribute("community",community);
        model.addAttribute("member",sessionMember);

        return "members/mypage";

    }

    private void myPageCommunityPaging(Model model, Page<Community> page, int nowPage, int startPage, int endPage) {
        //현재 페이지 변수 Pageable 0페이지부터 시작하기 +1을해줘서 1페이지부터 반영한다
        int nowPageB = page.getPageable().getPageNumber() + 1;
        //블럭에서 보여줄 시작페이지(Math.max 한이유는 시작페이지가 마이너스 값일 수는 업으니깐 Math.max를 사용)
        int startPageB =Math.max(nowPage -4,1) ;
        //블럭에서 보여줄때 마지막페이지(Math.min 한이유는 총페이지가 10페이지인데, 현재페이지가 9페이지이면 14페이지가되므로 오류,
        //그렇기에 getTotalpage를  min으로설정)
        int endPageB = Math.min(nowPage + 5, page.getTotalPages());
        model.addAttribute("nowPageB", nowPage);
        model.addAttribute("startPageB", startPage);
        model.addAttribute("endPageB", endPage);
    }

    private void myPageParticipationPaging(Model model, Page<Participation> page, int nowPage, int startPage, int endPage) {
        //현재 페이지 변수 Pageable 0페이지부터 시작하기 +1을해줘서 1페이지부터 반영한다
        int nowPageB = page.getPageable().getPageNumber() + 1;
        //블럭에서 보여줄 시작페이지(Math.max 한이유는 시작페이지가 마이너스 값일 수는 업으니깐 Math.max를 사용)
        int startPageB =Math.max(nowPage -4,1) ;
        //블럭에서 보여줄때 마지막페이지(Math.min 한이유는 총페이지가 10페이지인데, 현재페이지가 9페이지이면 14페이지가되므로 오류,
        //그렇기에 getTotalpage를  min으로설정)
        int endPageB = Math.min(nowPage + 5, page.getTotalPages());
        model.addAttribute("nowPageB", nowPage);
        model.addAttribute("startPageB", startPage);
        model.addAttribute("endPageB", endPage);
    }

    @GetMapping("/modifyPassword")
    public String changePasswordForm(Model model){

        model.addAttribute("updatePasswordDto", new UpdatePasswordDto());

        return "members/modifyPassword";
    }

    @PostMapping("/modifyPassword")
    public String updatePassword(@Validated @ModelAttribute UpdatePasswordDto updatePasswordDto, BindingResult bindingResult,
                                 @LoginUser SessionMember sessionMember, Model model){
        
        if (bindingResult.hasErrors()){
            return "members/modifyPassword";
        }
        String memberEmail = sessionMember.getMemberEmail();
        try {
            memberService.modifyPassword(updatePasswordDto, memberEmail);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "members/modifyPassword";
        }
        return "members/modifyPasswordSuccess";
    }

    //비밀번호 일치여부 initbinder
    @InitBinder("updatePasswordDto")
    public void initBinder(WebDataBinder webDataBinder){
        log.info("webDataBinder={}, target={}", webDataBinder, webDataBinder.getTarget());
        webDataBinder.addValidators(validator);
    }
}
