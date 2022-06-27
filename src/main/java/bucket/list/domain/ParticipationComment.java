package bucket.list.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//댓글 domain
@Getter
@Setter
@Entity
public class ParticipationComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer commentIdx;

    private String commentText;

    @ManyToOne
    @JoinColumn(name = "member_idx")
    private Member member; // 작성자


    @ManyToOne
    @JoinColumn(name = "participation_idx")
    private Participation participation;

    public void modifyComment(String commentText) {this.commentText = commentText;}

}
