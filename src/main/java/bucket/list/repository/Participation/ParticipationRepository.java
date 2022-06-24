package bucket.list.repository.Participation;

import bucket.list.domain.Community;
import bucket.list.domain.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation,Integer> {

    @Modifying
    @Query("update Participation p set p.participationCount = p.participationCount + 1 where p.participationIdx = :participationIdx")
    int updateCount(int participationIdx);


    @Query("select p from Participation p where p.participationWriter = :participationWriter")
    List<Participation> findAllWriteList(@Param("participationWriter") String participationWriter);


    @Query("select p.participationWriter from Participation p where p.participationIdx = :participationIdx")
    String findWriter(@Param("participationIdx") int participationIdx);

    @Query("select p from Participation p where  p.participationTag like %:keyword%  order by p.participationIdx desc")
    List<Participation> findByParticipationTagContaining(String keyword);

    @Query("select p from Participation p where p.participationWriter = :participationWriter and p.participationSubject like %:keyword% order by p.participationIdx desc")
    List<Participation> findByParticipationWriterAndParticipationSubjectContaining(String participationWriter,String keyword);


}
