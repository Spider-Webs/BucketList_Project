package bucket.list.controller;

import bucket.list.domain.Community;
import bucket.list.domain.Participation;
import bucket.list.service.Community.CommunityService;

import bucket.list.service.Participation.ParticipationService;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = {"/"})
public class MainController {
    private final CommunityService communityService;
    private final ParticipationService participationService;




    @GetMapping("")
    public String main(Model model, @PageableDefault(page=0, size=4) Pageable pageable, HttpServletResponse response){

        //viewCount 쿠키 생성
        Cookie cookie = new Cookie("viewCount", null);
        cookie.setComment("게시글 조회수 확인");  //해당 쿠키용도
        cookie.setMaxAge(60*60*24*365); //쿠키 유효시간 설정
        response.addCookie(cookie);

        Page<Community> data = mainCommunity(pageable);
        Page<Participation> data2 = mainParticipation(pageable);


        model.addAttribute("data",data);
        model.addAttribute("data2", data2);

        return "index";

    }


    public Page<Community> mainCommunity(@PageableDefault(page = 0, size=4, sort="communityid", direction = Sort.Direction.DESC)
            Pageable pageable){
        Page<Community> data =communityService.allContentList(pageable);

        return data;
    }

    public Page<Participation> mainParticipation(@PageableDefault(page = 0, size=4, sort="participationIdx", direction = Sort.Direction.DESC)
            Pageable pageable){
        Page<Participation> data2 = participationService.allContentList(pageable);
        return data2;
    }

}
