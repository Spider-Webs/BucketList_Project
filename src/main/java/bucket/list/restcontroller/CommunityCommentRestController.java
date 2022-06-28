package bucket.list.restcontroller;

import bucket.list.config.LoginUser;
import bucket.list.domain.CommunityComment;
import bucket.list.dto.SessionMember;
import bucket.list.service.Community.CommunityCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommunityCommentRestController {

    private final CommunityCommentService communityCommentService;

    //댓글 저장
    @PostMapping("/community/{communityIdx}/comments")
    public ResponseEntity commentSave(@PathVariable int communityIdx, @RequestBody CommunityComment communityComment,
                                      @LoginUser SessionMember sessionMember){
        return ResponseEntity.ok(communityCommentService.save(sessionMember.getMemberId(), communityIdx, communityComment));
    }

    //수정
    @PutMapping({"/community/{communityIdx}/comments/{commentIdx}"})
    public ResponseEntity modify(@PathVariable int commentIdx, @RequestBody CommunityComment communityComment){

        communityCommentService.modify(commentIdx,communityComment);
        return ResponseEntity.ok(commentIdx);
    }

    //삭제
    @DeleteMapping("/community/{communityIdx}/comments/{commentIdx}")
    public ResponseEntity delete(@PathVariable int commentIdx){
        communityCommentService.delete(commentIdx);
        return ResponseEntity.ok(commentIdx);
    }


}
