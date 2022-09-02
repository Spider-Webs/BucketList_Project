package bucket.list.communitydto;

import bucket.list.domain.Community;
import bucket.list.domain.CommunityComment;
import bucket.list.domain.Member;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CommunityCommentRequestDto {

    private Integer commentIdx;
    private String commentText;
    private Member member;
    private Community community;

    public CommunityComment toEntity(){
        CommunityComment communityComment = CommunityComment.builder()
                .commentIdx(commentIdx)
                .commentText(commentText)
                .member(member)
                .community(community)
                .build();

        return communityComment;
    }
}
