package bucket.list.domain;

import lombok.*;

import javax.persistence.*;

//댓글 domain
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class ParticipationComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer commentIdx;

    private String commentText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member; // 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participation_idx")
    private Participation participation;

    public void modifyComment(String commentText) {this.commentText = commentText;}

}
