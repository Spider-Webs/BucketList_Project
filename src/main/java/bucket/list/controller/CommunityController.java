package bucket.list.controller;

import bucket.list.config.LoginUser;
import bucket.list.domain.Community;
import bucket.list.domain.CommunityComment;
import bucket.list.domain.Participation;
import bucket.list.domain.ParticipationComment;
import bucket.list.dto.SessionMember;
import bucket.list.service.Community.CommunityCommentService;
import bucket.list.service.Community.CommunityService;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping(value="/community")
public class CommunityController {


    private final CommunityService communityService;
    private final CommunityCommentService communityCommentService;



    @GetMapping("")
    public String main(Model model, @PageableDefault(page = 0, size=8, sort="communityIdx", direction = Sort.Direction.DESC)
            Pageable pageable){        // 커뮤니티 메인

        Page<Community> data =communityService.allContentList(pageable);

        //현재 페이지 변수 Pageable 0페이지부터 시작하기 +1을해줘서 1페이지부터 반영한다
        int nowPage = data.getPageable().getPageNumber() + 1;
        //블럭에서 보여줄 시작페이지(Math.max 한이유는 시작페이지가 마이너스 값일 수는 업으니깐 Math.max를 사용)
        int startPage =Math.max(nowPage-4,1) ;
        //블럭에서 보여줄때 마지막페이지(Math.min 한이유는 총페이지가 10페이지인데, 현재페이지가 9페이지이면 14페이지가되므로 오류,
        //그렇기에 getTotalpage를  min으로설정)
        int endPage = Math.min(nowPage + 5, data.getTotalPages());

        model.addAttribute("data",data);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        return "community/main";


    }


    @GetMapping("/login/create")
    public String createForm(Model model){        // 게시물 작성 폼

        model.addAttribute("community", new Community());

        return "community/write";
    }

    @PostMapping("/login/create")
    public String createCommunity(@LoginUser SessionMember sessionMember, Community community, MultipartFile file,Model model) throws IOException {        // 커뮤니티 게시물 작성 폼


        if(sessionMember.getMemberId()!=null) {
            community.setCommunityWriter(sessionMember.getMemberId());
            communityService.createCommunity(community, file);
        }else{
            community.setCommunityWriter("SNS_"+sessionMember.getMemberName());
            communityService.createCommunity(community, file);
        }

        return "redirect:/community";
    }

    @GetMapping("/{communityIdx}")       // 해당 게시물 출력
    public String communityDetail(@PathVariable("communityIdx")int communityIdx, @LoginUser SessionMember sessionMember,
                                  @CookieValue(name = "viewCount") String cookie, HttpServletResponse response, Model model){

        if(!(cookie.contains(String.valueOf(communityIdx)))){
            cookie += communityIdx + "/";
            communityService.updateCount(communityIdx);
        }
        response.addCookie(new Cookie("viewCount",cookie));

        String writer = communityService.findWriter(communityIdx);       // 해당 게시물의 writer 정보를 dbwriter에 저장함

        Community community = communityService.oneContentList(communityIdx);

        List<CommunityComment> comments = community.getComments();


        model.addAttribute("commentWriter",sessionMember.getMemberId());
        try{
            if(sessionMember !=null&&sessionMember.getMemberId().equals(writer)) {
                sendCommunity(communityIdx,model,community,comments);
                model.addAttribute("writer", writer);
            }else{
                sendCommunity(communityIdx,model,community,comments);
            }
            return "community/read";
        } catch (NullPointerException e){
            sendCommunity(communityIdx,model,community,comments);
            return "community/read";
        }


    }

    private void sendCommunity(int communityIdx, Model model, Community community, List<CommunityComment> communityComments) {
        model.addAttribute("comments", communityComments);
        model.addAttribute("community", community);
        model.addAttribute("communityIdx", communityIdx);
    }

    @PostMapping("/login/comment/{communityIdx}")
    //댓글 저장
    public String comment(@PathVariable int communityIdx, @LoginUser SessionMember sessionMember,
                          @ModelAttribute("comment") CommunityComment communityComment, Model model){


        communityCommentService.save(sessionMember.getMemberId(), communityIdx, communityComment);


        return "redirect:/community/{communityIdx}";


    }


    @GetMapping("/edit/{communityIdx}")      // 게시글 수정을 위한 form 페이지(이전 값 불러옴)
    public String communityModify(@PathVariable("communityIdx") int communityIdx, Model model){
        Community community = communityService.oneContentList(communityIdx);
        model.addAttribute("community", community);
        model.addAttribute("number", communityIdx);
        return "community/edit";
    }

    @PostMapping("/edit/{communityIdx}")       // 게시글 수정
    public String communityModify(Community community, @PathVariable("communityIdx") int communityIdx,MultipartFile file,@LoginUser SessionMember sessionMember) throws IOException {


        community.setCommunityDate(LocalDate.now());
        community.setCommunityWriter(sessionMember.getMemberId());
        communityService.createCommunity(community,file);

        return "redirect:/community/{communityIdx}";
    }

    @GetMapping("/delete/{communityIdx}")         // 게시물 삭제
    public String communityDelete(int communityIdx){
        communityService.communityDelete(communityIdx);
        return "redirect:/community";
    }

    //마이페이지에서 내가 작성 게판목록 검색컨틀로러
    @PostMapping("myWriteSearch")
    public String myWriteSearch(@RequestParam String keyword,@LoginUser SessionMember sessionMember, Model model){

        List<Community> myWriteSearch = communityService.myWriteSearch(sessionMember.getMemberId(), keyword);

        model.addAttribute("myWriteSearch", myWriteSearch);


        return "community/myWriteSearch";
    }

}
