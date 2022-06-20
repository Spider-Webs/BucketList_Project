package bucket.list.controller;

import bucket.list.domain.Member;
import bucket.list.domain.User;
import bucket.list.dto.MailDto;
import bucket.list.dto.MemberFormDto;
import bucket.list.service.Member.MemberService;
import bucket.list.validator.MemberValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;



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


            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);

//        }catch (IllegalStateException e){
//            System.out.println("controller 오류발생");
//            model.addAttribute("errorMessage", e.getMessage());
//            return "members/create";
//        }


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

    @GetMapping("findPassword")
    public String findPassword(){

        return "members/findPassword";
    }

    @PostMapping("findPassword")
    public String sendMail(@RequestParam String memberEmail,Model model){

        try {
            memberService.equalEmail(memberEmail);
            MailDto mailDto = memberService.changePassword(memberEmail);
            memberService.mailSend(mailDto);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "members/findPassword";
        }

        return "members/sendMailSuccess";
    }



    //비밀번호 일치여부 initbinder
    @InitBinder("Member")
    public void initBinder(WebDataBinder webDataBinder){
        MemberValidator memberValidator = new MemberValidator();

        webDataBinder.addValidators(memberValidator);
    }
}
