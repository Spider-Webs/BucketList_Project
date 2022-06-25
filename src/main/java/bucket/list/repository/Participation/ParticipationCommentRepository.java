package bucket.list.repository.Participation;

import bucket.list.domain.ParticipationComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationCommentRepository extends JpaRepository<ParticipationComment,Integer> {


    //댓글리스트
    @Query("select c from ParticipationComment c where c.commentNumber = :commentNumber order by c.commentIdx desc")
    List<ParticipationComment> allContentList(@Param("commentNumber") int commentNumber);

    //댓글수정
    @Query("update ParticipationComment c set c.commentText = :commentText where c.commentIdx = :commentIdx")
    String commentEdit(@Param("commentIdx") String commentText, int commentIdx);


    //댓글작성자
    @Query("select c.commentWriter from ParticipationComment c where c.commentIdx = :commentIdx")
    String findCommentWriter(@Param("commentIdx") int commentIdx);

}
