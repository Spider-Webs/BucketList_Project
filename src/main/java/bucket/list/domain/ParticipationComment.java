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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentIdx;

    private String commentText;

    private String commentWriter;

    private int commentNumber;

    public void modifyComment(String commentText) {this.commentText = commentText;}

}
