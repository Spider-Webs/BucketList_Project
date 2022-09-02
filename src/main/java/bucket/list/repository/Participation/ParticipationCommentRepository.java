package bucket.list.repository.Participation;

import bucket.list.domain.ParticipationComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationCommentRepository extends JpaRepository<ParticipationComment,Integer> {



}
