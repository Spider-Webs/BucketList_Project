package bucket.list.restcontroller;

import bucket.list.config.LoginUser;
import bucket.list.communitydto.CommunityCommentRequestDto;
import bucket.list.memberdto.SessionMember;
import bucket.list.service.Community.CommunityCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommunityCommentRestController {

    private final CommunityCommentService communityCommentService;

    //댓글 저장
    @PostMapping("/community/{communityIdx}/comments")
    public ResponseEntity commentSave(@PathVariable int communityIdx, @RequestBody CommunityCommentRequestDto communityCommentRequestDto,
                                      @LoginUser SessionMember sessionMember){
        log.info("Idx :{}, dtp:{}, member:{}",communityIdx,communityCommentRequestDto.getCommentText(),sessionMember.getMemberId());
        return ResponseEntity.ok(communityCommentService.save(sessionMember.getMemberId(), communityIdx, communityCommentRequestDto));
    }

    //수정
    @PutMapping({"/community/{communityIdx}/comments/{commentIdx}"})
    public ResponseEntity modify(@PathVariable int commentIdx, @RequestBody CommunityCommentRequestDto communityCommentRequestDto){

        communityCommentService.modify(commentIdx, communityCommentRequestDto);
        return ResponseEntity.ok(commentIdx);
    }

    //삭제
    @DeleteMapping("/community/{communityIdx}/comments/{commentIdx}")
    public ResponseEntity delete(@PathVariable int commentIdx){
        communityCommentService.delete(commentIdx);
        return ResponseEntity.ok(commentIdx);
    }


}
