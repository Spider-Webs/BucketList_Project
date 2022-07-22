package bucket.list.controller;


import bucket.list.config.LoginUser;
import bucket.list.domain.ParticipationComment;
import bucket.list.domain.Participation;
import bucket.list.memberdto.SessionMember;
import bucket.list.participationdto.ParticipationCommentResponseDto;
import bucket.list.participationdto.ParticipationRequestDto;
import bucket.list.participationdto.ParticipationResponseDto;
import bucket.list.service.Participation.ParticipationCommentService;
import bucket.list.service.Participation.ParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/participation")
public class ParticipationController {

    private final ParticipationService participationService;
    private final ParticipationCommentService participationCommentService;

    //전체 글보여주는 페이지
    //participationIdx 기준으로 정렬
   @GetMapping
   public String items(Model model,@PageableDefault(page = 0, size = 8,
            sort = "participationIdx",direction = Sort.Direction.DESC) Pageable pageable){
        Page<Participation> items = participationService.participationList(pageable);
        participationPaging(model, items);
        model.addAttribute("items", items);

        return "participation/main";
    }

    @GetMapping("/login/write")
    //글 추가하는 view보여주는 메서드
    public String addForm(Model model){

        model.addAttribute("participation", new Participation());

        return "participation/write";
    }

    @PostMapping("/login/write")
    public String add(@LoginUser SessionMember sessionMember, Model model,
                      ParticipationRequestDto participationRequestDto, MultipartFile file) throws IOException {

            participationService.save(participationRequestDto, file,sessionMember.getMemberId());

        return "redirect:/participation";
    }
    @GetMapping("{participationIdx}")
    //전체게시글에서 하나의 게시글 클릭시 하나의게시글보여주는 메서드
    public String item(@PathVariable int participationIdx, @LoginUser SessionMember sessionMember,
                       @CookieValue(name = "viewCount") String cookie, HttpServletResponse response,Model model){

        if(!(cookie.contains(String.valueOf(participationIdx)))){
            cookie += participationIdx + "/";
            participationService.updateCount(participationIdx);
        }
        response.addCookie(new Cookie("viewCount",cookie));


        ParticipationResponseDto participationResponseDto = participationService.findParticipation(participationIdx);
        List<ParticipationCommentResponseDto> participationComments = participationResponseDto.getComments();

            try{
                if(sessionMember !=null&&sessionMember.getMemberId().equals(participationResponseDto.getMember().getMemberId())) {
                    sendParticipation(participationIdx, model, participationResponseDto, participationComments,sessionMember);
                }else{
                    sendParticipation(participationIdx, model, participationResponseDto, participationComments,sessionMember);
                }
                return "participation/read";
            } catch (NullPointerException e){
                sendParticipation(participationIdx, model, participationResponseDto, participationComments,sessionMember);
                return "participation/read";
            }
    }

    private void sendParticipation(int participationIdx, Model model, ParticipationResponseDto participation, List<ParticipationCommentResponseDto> participationComments,@LoginUser SessionMember sessionMember) {
        model.addAttribute("participationComments", participationComments);
        model.addAttribute("participation", participation);
        model.addAttribute("participationIdx", participationIdx);
        model.addAttribute("sessionMember", sessionMember.getMemberId());
    }


    @GetMapping("/edit/{participationIdx}")
    //게시글 수정 view 보여주고 전달
    public String editForm(@PathVariable int participationIdx, Model model){
        ParticipationResponseDto participation = participationService.findParticipation(participationIdx);
        model.addAttribute("participation", participation);
        model.addAttribute("number", participationIdx);
        return "participation/edit";
    }

    @PostMapping("/edit/{participationIdx}")
    //실제 게시글수정, 파일이미지 업로드
    public String edit(ParticipationRequestDto participationRequestDto,@PathVariable int participationIdx,MultipartFile file,@LoginUser SessionMember sessionMember) throws IOException {

        participationService.save(participationRequestDto,file,sessionMember.getMemberId());

        return "redirect:/participation/{participationIdx}";
    }

    @GetMapping("/delete/{participationIdx}")
    //게시글 삭제
    public String delete(@PathVariable int participationIdx){

        participationService.deleteParticipation(participationIdx);

        return "redirect:/participation";
    }
    @GetMapping("/commentEdit/{commentIdx}/{participationIdx}")
    public String commentEdit(@PathVariable int participationIdx,
                              @PathVariable int commentIdx,
                              Model model){

        return "redirect:/participation/{participationIdx}";
    }

    @PostMapping("/search")
    public String search(@RequestParam String keyword,Model model){
        List<Participation> searchList = participationService.search(keyword);

        model.addAttribute("searchList", searchList);

        return "participation/search";
    }

    @PostMapping("myWriteSearch")
    public String myWriteSearch(@RequestParam String keyword,@LoginUser SessionMember sessionMember, Model model,@PageableDefault(page = 0, size = 8,
            sort = "participationIdx",direction = Sort.Direction.DESC) Pageable pageable){

       Page<Participation> myWriteSearch = participationService.myWriteSearch(sessionMember.getMemberId(), keyword,pageable);

        participationPaging(model, myWriteSearch);
        model.addAttribute("myWriteSearch", myWriteSearch);

        return "participation/myWriteSearch";
    }
    //참여하기, 페이징처리를 위한 메서드
    private void participationPaging(Model model, Page<Participation> page) {
        //현재 페이지 변수 Pageable 0페이지부터 시작하기 +1을해줘서 1페이지부터 반영한다
        int nowPage = page.getPageable().getPageNumber() + 1;
        //블럭에서 보여줄 시작페이지(Math.max 한이유는 시작페이지가 마이너스 값일 수는 업으니깐 Math.max를 사용)
        int startPage =Math.max(nowPage-4,1) ;
        //블럭에서 보여줄때 마지막페이지(Math.min 한이유는 총페이지가 10페이지인데, 현재페이지가 9페이지이면 14페이지가되므로 오류,
        //그렇기에 getTotalpage를  min으로설정)
        int endPage = Math.min(nowPage + 5, page.getTotalPages());

        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
    }
}
