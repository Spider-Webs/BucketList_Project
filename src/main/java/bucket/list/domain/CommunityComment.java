package bucket.list.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class CommunityComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer commentIdx;

    private String commentText;

    @ManyToOne
    @JoinColumn(name = "member_idx")
    private Member member; // 작성자

    @ManyToOne
    @JoinColumn(name = "community_idx")
    private Community community;

    public void modifyComment(String commentText) {this.commentText = commentText;}
}
