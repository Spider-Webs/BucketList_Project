package bucket.list.participationdto;

import bucket.list.domain.CommunityComment;
import bucket.list.domain.Member;
import bucket.list.domain.Participation;
import bucket.list.domain.ParticipationComment;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ParticipationCommentResponseDto {

    private Integer commentIdx;
    private String commentText;
    private Member member; // 작성자
    private Participation participation;

    public ParticipationCommentResponseDto(ParticipationComment comment){
        this.commentIdx = comment.getCommentIdx();
        this.commentText = comment.getCommentText();
        this.member = comment.getMember();
        this.participation = comment.getParticipation();
    }
}
