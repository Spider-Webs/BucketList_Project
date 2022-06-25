package bucket.list.controller;

import bucket.list.config.LoginUser;
import bucket.list.domain.Member;
import bucket.list.domain.Role;
import bucket.list.dto.*;
import bucket.list.service.Community.CommunityService;
import bucket.list.service.Member.MemberService;
import bucket.list.service.Participation.ParticipationService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



import javax.validation.Valid;


@RequiredArgsConstructor
@Controller
@RequestMapping("/members")
public class MemberController {


    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final ParticipationService participationService;
    private final CommunityService communityService;

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
            memberService.mailSend(mailDto);
        }catch (IllegalStateException e){

            model.addAttribute("errorMessage", e.getMessage());
            return "members/findPassword";
        }

        return "members/sendMailSuccess";
    }

    @GetMapping("/mypage")
    public String myPage(@LoginUser SessionMember sessionMember, Model model){


            model.addAttribute("participation",participationService.findAllWriteList(sessionMember.getMemberId()));
            model.addAttribute("community",communityService.findAllWriteList(sessionMember.getMemberId()));
            model.addAttribute("member",sessionMember);

            return "members/mypage";


    }

    @GetMapping("/modifyPassword")
    public String changePasswordForm(Model model){

        model.addAttribute("updatePasswordDto", new UpdatePasswordDto());


        return "members/modifyPassword";
    }

    @PostMapping("/modifyPassword")
    public String updatePassword(@Valid @ModelAttribute UpdatePasswordDto updatePasswordDto, BindingResult bindingResult,
                                 @LoginUser SessionMember sessionMember){
        
        if (bindingResult.hasErrors()){
            return "members/modifyPassword";
        }

        String memberEmail = sessionMember.getMemberEmail();

        memberService.modifyPassword(updatePasswordDto, memberEmail);

        return "members/modifyPasswordSuccess";

    }


    //비밀번호 일치여부 initbinder
//    @InitBinder
//    public void initBinder(WebDataBinder webDataBinder){
//        MemberValidator memberValidator = new MemberValidator();
//
//        webDataBinder.addValidators(memberValidator);
//    }
}
