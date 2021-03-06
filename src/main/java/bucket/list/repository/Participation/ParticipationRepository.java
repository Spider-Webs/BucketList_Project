package bucket.list.repository.Participation;

import bucket.list.domain.Community;
import bucket.list.domain.Member;
import bucket.list.domain.Participation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation,Integer> {

    //조회수 증가 쿼리
    @Modifying
    @Query("update Participation p set p.participationCount = p.participationCount + 1 where p.participationIdx = :participationIdx")
    int updateCount(int participationIdx);

    //작성자가 작성한 게시글 가져오기 쿼리
    @Query("select p from Participation p where p.member.memberId = :memberId")
    Page<Participation> findByMember(String memberId, Pageable pageable);


    //메인페이지 조회쿼리
    @Query("select p from Participation p where  p.participationTag like %:keyword%  order by p.participationIdx desc")
    List<Participation> findByParticipationTagContaining(String keyword);

    //마이페이지 조회쿼리
    @Query("select p from Participation p where p.member.memberId = :memberId and p.participationSubject like %:keyword% order by p.participationIdx desc")
    Page<Participation> findByParticipationWriterAndParticipationSubjectContaining(String memberId,String keyword,Pageable pageable);


}
