package bucket.list.repository.Participation;

import bucket.list.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {


    //댓글리스트
    @Query("select c from Comment c where c.commentNumber = :commentNumber order by commentIdx desc")
    List<Comment> allContentList(@Param("commentNumber") int commentNumber);

    //댓글수정
    @Query("update Comment c set c.commentText = :commentText where commentIdx = :commentIdx")
    String commentEdit(@Param("commentIdx") String commentText, int commentIdx);


    //댓글작성자
    @Query("select c.commentWriter from Comment c where commentIdx = :commentIdx")
    String findCommentWriter(@Param("commentIdx") int commentIdx);

}
