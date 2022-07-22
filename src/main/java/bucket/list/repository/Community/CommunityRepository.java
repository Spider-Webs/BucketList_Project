package bucket.list.repository.Community;



import bucket.list.domain.Community;
import bucket.list.domain.Participation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community,Object> {

    //작성자의 게시글 목록가져오기
    @Query(value = "select c from Community c where c.member.memberId = :memberId")
    Page<Community> findAllWriteList(String memberId, Pageable pageable);


    @Modifying
    @Query("update Community c set c.communityCount = c.communityCount + 1 where c.communityIdx = :communityIdx")
    int updateCount(int communityIdx);

    //마이페이지 조회쿼리
    @Query("select c from Community c where c.member.memberId = :memberId and c.communitySubject like %:keyword% order by c.communityIdx desc")
    Page<Community> findByCommunityWriterAndCommunitySubjectContaining(String memberId, String keyword,Pageable pageable);




}
