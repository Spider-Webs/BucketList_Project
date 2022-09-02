package bucket.list.restcontroller;

import bucket.list.config.LoginUser;
import bucket.list.domain.ParticipationComment;
import bucket.list.memberdto.SessionMember;
import bucket.list.participationdto.ParticipationCommentRequestDto;
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
    public ResponseEntity commentSave(@PathVariable int participationIdx, @RequestBody ParticipationCommentRequestDto participationComment,
                                      @LoginUser SessionMember sessionMember){
        return ResponseEntity.ok(participationCommentService.save(sessionMember.getMemberId(), participationIdx, participationComment));
    }

    //수정
    @PutMapping({"/participation/{participationIdx}/comments/{commentIdx}"})
    public ResponseEntity modify(@PathVariable int commentIdx, @RequestBody ParticipationCommentRequestDto participationComment){

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
