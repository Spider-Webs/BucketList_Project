package bucket.list.controller;

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

    @PostMapping("/posts/{communityIdx}/comments")
    public ResponseEntity commentSave(@PathVariable int communityIdx, @RequestBody CommunityComment communityComment,
                                      @LoginUser SessionMember sessionMember){
        return ResponseEntity.ok(communityCommentService.save(sessionMember.getMemberId(), communityIdx, communityComment));
    }
}
