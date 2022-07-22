package bucket.list.participationdto;

import bucket.list.domain.CommunityComment;
import bucket.list.domain.Member;
import bucket.list.domain.Participation;
import bucket.list.domain.ParticipationComment;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ParticipationCommentRequestDto {

    private Integer commentIdx;
    private String commentText;
    private Member member; // 작성자
    private Participation participation;

    public ParticipationComment toEntity() {
        ParticipationComment participationComment = ParticipationComment.builder()
                .commentIdx(commentIdx)
                .commentText(commentText)
                .member(member)
                .participation(participation)
                .build();

        return participationComment;
    }
}
