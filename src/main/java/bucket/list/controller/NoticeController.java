package bucket.list.controller;


import bucket.list.config.LoginUser;
import bucket.list.domain.Notice;
import bucket.list.dto.NoticeDto;
import bucket.list.memberdto.SessionMember;
import bucket.list.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    //공지사항 메인페이지 메서드
    @GetMapping()
    //페이징구현하기, Pageable 사용하기 page = 기본페이지, size 한페이지 게시글수,sort 정렬 기준 잡을 변수, direction 오름차순인지 내림차순인지
    public String findNoticeList(Model model,
                         @PageableDefault(page = 0, size = 10, sort = "noticeNumber",direction = Sort.Direction.DESC) Pageable pageable,
                         @LoginUser SessionMember member) {
        long start = System.currentTimeMillis();
        Page<Notice> contents = noticeService.noticeList(pageable);

        model.addAttribute("member", member);
        getPageNumber(model, contents);
        long end = System.currentTimeMillis();
        log.info("걸린 시간 : {}",(end-start));
        return "notice/main";

    }

    private void getPageNumber(Model model, Page<Notice> contents) {
        //현재 페이지 변수 Pageable 0페이지부터 시작하기 +1을해줘서 1페이지부터 반영한다
        int nowPage = contents.getPageable().getPageNumber() + 1;
        //블럭에서 보여줄 시작페이지(Math.max 한이유는 시작페이지가 마이너스 값일 수는 업으니깐 Math.max를 사용)
        int startPage = Math.max(nowPage - 4, 1);
        //블럭에서 보여줄때 마지막페이지(Math.min 한이유는 총페이지가 10페이지인데, 현재페이지가 9페이지이면 14페이지가되므로 오류,
        //그렇기에 getTotalpage를  min으로설정)
        int endPage = Math.min(nowPage + 5, contents.getTotalPages());

        model.addAttribute("contents", contents);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
    }

    //글쓰기페이지
    @GetMapping("/write")
    public String writeForm(Model model ){

        model.addAttribute("notice", new NoticeDto());

        return "notice/write";
    }


    @PostMapping("/write")
    public String write(@Valid @ModelAttribute("notice") NoticeDto noticeDto, MultipartFile file, @LoginUser SessionMember sessionMember, BindingResult bindingResult) throws IOException {

        if(bindingResult.hasErrors()){
            return "notice/write";
        }

        log.info("text: {},id: {}, file{}",noticeDto.getNoticeText(),noticeDto.getNoticeNumber(),file);
        noticeService.save(noticeDto,file,sessionMember.getMemberId());

        return "redirect:/notice";

    }
    @GetMapping("/{noticeNumber}/read")
    //글읽는 페이지 메서드
    public String read(@LoginUser SessionMember sessionMember,@PathVariable Integer noticeNumber, Model model  ){

        Notice notice = noticeService.findNotice(noticeNumber);

        if(sessionMember != null){
            model.addAttribute("notice",notice);
            model.addAttribute("securityMember", sessionMember);
        }else{
            model.addAttribute("notice",notice);

        }
        return "notice/read";
    }

    @GetMapping("/edit/{noticeNumber}")
    //게시글 수정 view 보여주고 전달
    public String editForm(@PathVariable int noticeNumber, Model model){
        Notice notice = noticeService.findNotice(noticeNumber);
        model.addAttribute("notice", notice);
        model.addAttribute("number", noticeNumber);
        return "notice/edit";
    }

    @PostMapping("/edit/{noticeNumber}")
    //실제 게시글수정, 파일이미지 업로드
    public String edit(@ModelAttribute("notice") NoticeDto noticeDto, @PathVariable int noticeNumber, MultipartFile file, @LoginUser SessionMember sessionMember) throws IOException {
        noticeService.save(noticeDto,file,sessionMember.getMemberId());
        return "redirect:/notice/{noticeNumber}/read";
    }

    @GetMapping("/delete/{noticeNumber}")
    //게시글 삭제
    public String delete(@PathVariable int noticeNumber){

        noticeService.deleteNotice(noticeNumber);

        return "redirect:/notice";
    }
}
