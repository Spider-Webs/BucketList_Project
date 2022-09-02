package bucket.list.communitydto;

import bucket.list.domain.Community;
import bucket.list.domain.CommunityComment;
import bucket.list.domain.Member;
import lombok.Getter;

@Getter
public class CommunityCommentResponseDto {

    private Integer commentIdx;
    private String commentText;
    private Member member;
    private Community community;

    public CommunityCommentResponseDto(CommunityComment comment){
        this.commentIdx = comment.getCommentIdx();
        this.commentText = comment.getCommentText();
        this.member = comment.getMember();
        this.community = comment.getCommunity();
    }

}
