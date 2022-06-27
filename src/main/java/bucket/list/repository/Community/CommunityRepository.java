package bucket.list.repository.Community;

//import spring.basic.demo.domain.Data;


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
    @Query(value = "select c from Community c where c.communityWriter = :communityWriter")
    Page<Community> findAllWriteList(String communityWriter, Pageable pageable);

    //특정 게시글에대한 작성자가 누구인지 확인하는 쿼리
    @Query(value = "select c.communityWriter from Community c where c.communityIdx = :communityIdx")
    String findWriter(int communityIdx);

    @Modifying
    @Query("update Community c set c.communityCount = c.communityCount + 1 where c.communityIdx = :communityIdx")
    int updateCount(int communityIdx);

    //마이페이지 조회쿼리
    @Query("select c from Community c where c.communityWriter = :communityWriter and c.communitySubject like %:keyword% order by c.communityIdx desc")
    Page<Community> findByCommunityWriterAndCommunitySubjectContaining(String communityWriter, String keyword,Pageable pageable);




}
