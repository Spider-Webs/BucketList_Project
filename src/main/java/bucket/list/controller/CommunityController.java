package bucket.list.controller;

import bucket.list.communitydto.CommunityCommentResponseDto;
import bucket.list.config.LoginUser;
import bucket.list.domain.Community;
import bucket.list.communitydto.CommunityRequestDto;
import bucket.list.communitydto.CommunityResponseDto;
import bucket.list.memberdto.SessionMember;
import bucket.list.service.Community.CommunityCommentService;
import bucket.list.service.Community.CommunityService;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
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

        communityPaging(model,data);

        model.addAttribute("data",data);

        return "community/main";
    }

    @GetMapping("/login/create")
    public String createForm(Model model){        // 게시물 작성 폼

        model.addAttribute("community", new Community());

        return "community/write";
    }

    @PostMapping("/login/create")
    public String createCommunity(@LoginUser SessionMember sessionMember, CommunityRequestDto communityRequestDto, MultipartFile file, Model model) throws IOException {        // 커뮤니티 게시물 작성 폼

            communityService.save(communityRequestDto, file,sessionMember.getMemberId());

        return "redirect:/community";
    }

    @GetMapping("/{communityIdx}")       // 해당 게시물 상세보기
    public String communityDetail(@PathVariable("communityIdx")int communityIdx, @LoginUser SessionMember sessionMember,
                                  @CookieValue(name = "viewCount") String cookie, HttpServletResponse response, Model model){

        if(!(cookie.contains(String.valueOf(communityIdx)))){
            cookie += communityIdx + "/";
            communityService.updateCount(communityIdx);
        }
        response.addCookie(new Cookie("viewCount",cookie));

        CommunityResponseDto communityResponseDto = communityService.oneContentList(communityIdx);

        List<CommunityCommentResponseDto> comments = communityResponseDto.getComments();

        try{
            if(sessionMember !=null&&sessionMember.getMemberId().equals(communityResponseDto.getMember().getMemberId())) {
                sendCommunity(communityIdx,model, communityResponseDto,comments,sessionMember);
            }else{
                sendCommunity(communityIdx,model, communityResponseDto,comments,sessionMember);
            }
            return "community/read";
        } catch (NullPointerException e){
            sendCommunity(communityIdx,model, communityResponseDto,comments,sessionMember);
            return "community/read";
        }

    }

    private void sendCommunity(int communityIdx, Model model, CommunityResponseDto community, List<CommunityCommentResponseDto> communityComments, @LoginUser SessionMember sessionMember) {
        model.addAttribute("comments", communityComments);
        model.addAttribute("community", community);
        model.addAttribute("communityIdx", communityIdx);
        model.addAttribute("sessionMember", sessionMember.getMemberId());
    }

    @GetMapping("/edit/{communityIdx}")      // 게시글 수정을 위한 form 페이지(이전 값 불러옴)
    public String communityModify(@PathVariable int communityIdx, Model model){
        CommunityResponseDto community = communityService.oneContentList(communityIdx);
        model.addAttribute("community", community);
        model.addAttribute("number", communityIdx);
        return "community/edit";
    }

    @PostMapping("/edit/{communityIdx}")       // 게시글 수정
    public String communityModify(CommunityRequestDto communityRequestDto, @PathVariable int communityIdx, MultipartFile file, @LoginUser SessionMember sessionMember) throws IOException {

            communityService.save(communityRequestDto, file,sessionMember.getMemberId());
            
        return "redirect:/community/{communityIdx}";
    }

    @GetMapping("/delete/{communityIdx}")         // 게시물 삭제
    public String communityDelete(int communityIdx){
        communityService.communityDelete(communityIdx);
        return "redirect:/community";
    }

    //마이페이지에서 내가 작성 게판목록 검색컨틀로러
    @PostMapping("myWriteSearch")
    public String myWriteSearch(@RequestParam String keyword,@LoginUser SessionMember sessionMember, Model model,@PageableDefault(page = 0, size=8, sort="communityIdx", direction = Sort.Direction.DESC)
            Pageable pageable){

        Page<Community> myWriteSearch = communityService.myWriteSearch(sessionMember.getMemberId(), keyword,pageable);
        //현재 페이지 변수 Pageable 0페이지부터 시작하기 +1을해줘서 1페이지부터 반영한다
        communityPaging(model, myWriteSearch);
        model.addAttribute("myWriteSearch", myWriteSearch);

        return "community/myWriteSearch";
    }

    //커뮤니티페이징 처리를 위한 메서드
    private void communityPaging(Model model, Page<Community> page) {
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
