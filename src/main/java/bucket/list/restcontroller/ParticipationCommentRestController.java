package bucket.list.restcontroller;

import bucket.list.config.LoginUser;
import bucket.list.domain.CommunityComment;
import bucket.list.domain.ParticipationComment;
import bucket.list.dto.SessionMember;
import bucket.list.service.Community.CommunityCommentService;
import bucket.list.service.Participation.ParticipationCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ParticipationCommentRestController {

    private final ParticipationCommentService participationCommentService;

    //댓글 저장
    @PostMapping("/participation/{participationIdx}/comments")
    public ResponseEntity commentSave(@PathVariable int participationIdx, @RequestBody ParticipationComment participationComment,
                                      @LoginUser SessionMember sessionMember){
        return ResponseEntity.ok(participationCommentService.save(sessionMember.getMemberId(), participationIdx, participationComment));
    }

    //수정
    @PutMapping({"/participation/{participationIdx}/comments/{commentIdx}"})
    public ResponseEntity modify(@PathVariable int commentIdx, @RequestBody ParticipationComment participationComment){


        participationCommentService.modify(commentIdx,participationComment);
        return ResponseEntity.ok(commentIdx);
    }

    //삭제
    @DeleteMapping("/participation/{participationIdx}/comments/{commentIdx}")
    public ResponseEntity delete(@PathVariable int commentIdx){
        participationCommentService.delete(commentIdx);
        return ResponseEntity.ok(commentIdx);
    }


}
