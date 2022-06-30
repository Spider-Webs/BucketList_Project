package bucket.list.controller;


import bucket.list.config.LoginUser;
import bucket.list.domain.About;
import bucket.list.dto.AboutDto;
import bucket.list.memberdto.SessionMember;
import bucket.list.service.about.AboutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/about")
public class AboutController {

    private final AboutService aboutService;

    //공지사항 메인페이지 메서드
    @GetMapping()
    //페이징구현하기, Pageable 사용하기 page = 기본페이지, size 한페이지 게시글수,sort 정렬 기준 잡을 변수, direction 오름차순인지 내림차순인지
    public String about( Model model,
                         @PageableDefault(page = 0, size = 10, sort = "aboutNumber",direction = Sort.Direction.DESC) Pageable pageable,
                         @LoginUser SessionMember member) {

        Page<About> abouts = aboutService.allContentList(pageable);

        model.addAttribute("member", member);
        getPageNumber(model, abouts);
        return "about/main";

    }

    private void getPageNumber(Model model, Page<About> abouts) {
        //현재 페이지 변수 Pageable 0페이지부터 시작하기 +1을해줘서 1페이지부터 반영한다
        int nowPage = abouts.getPageable().getPageNumber() + 1;
        //블럭에서 보여줄 시작페이지(Math.max 한이유는 시작페이지가 마이너스 값일 수는 업으니깐 Math.max를 사용)
        int startPage = Math.max(nowPage - 4, 1);
        //블럭에서 보여줄때 마지막페이지(Math.min 한이유는 총페이지가 10페이지인데, 현재페이지가 9페이지이면 14페이지가되므로 오류,
        //그렇기에 getTotalpage를  min으로설정)
        int endPage = Math.min(nowPage + 5, abouts.getTotalPages());

        model.addAttribute("abouts", abouts);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
    }

    //글쓰기페이지
    @GetMapping("/write")
    public String writeForm( ){

        return "about/write";
    }


    @PostMapping("/write")
    public String write(@ModelAttribute("about") AboutDto aboutDto, MultipartFile file, @LoginUser SessionMember sessionMember) throws IOException {
        log.info("text: {},id: {}, file{}",aboutDto.getAboutText(),aboutDto.getAboutNumber(),file);
        aboutService.save(aboutDto,file,sessionMember.getMemberId());

        return "redirect:/about";

    }
    @GetMapping("/{aboutNumber}/read")
    //글읽는 페이지 메서드
    public String read(@LoginUser SessionMember sessionMember,@PathVariable Integer aboutNumber, Model model  ){

        About about = aboutService.oneContentList(aboutNumber);

        if(sessionMember != null){
            model.addAttribute("about",about);
            model.addAttribute("securityMember", sessionMember);
            return "about/read";
        }else{
            model.addAttribute("about",about);
            return "about/read";
        }

    }

    @GetMapping("/edit/{aboutNumber}")
    //게시글 수정 view 보여주고 전달
    public String editForm(@PathVariable int aboutNumber, Model model){
        About about = aboutService.oneContentList(aboutNumber);
        model.addAttribute("about", about);
        model.addAttribute("number", aboutNumber);
        return "about/edit";
    }

    @PostMapping("/edit/{aboutNumber}")
    //실제 게시글수정, 파일이미지 업로드
    public String edit(@ModelAttribute("about") AboutDto aboutDto,@PathVariable int aboutNumber,MultipartFile file,@LoginUser SessionMember sessionMember) throws IOException {
        aboutService.save(aboutDto,file,sessionMember.getMemberId());
        return "redirect:/about/{aboutNumber}/read";
    }

    @GetMapping("/delete/{aboutNumber}")
    //게시글 삭제
    public String delete(@PathVariable int aboutNumber){

        aboutService.deleteContent(aboutNumber);

        return "redirect:/about";
    }
}
