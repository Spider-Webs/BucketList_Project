package bucket.list.repository.Member;

import bucket.list.domain.Member;
import bucket.list.domain.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    //중복확인을 위한 메서드
    Optional<Member> findByMemberId(String memberId);

    Optional<Member> findByMemberEmail(String memberEmail);

    @Modifying
    @Query(value = "update Member m set m.memberPassword = :memberPassword where m.memberIdx = :memberIdx")
    void tempUpdatePassword(Long memberIdx, String memberPassword);

    @Query("select m from Member m where  m.memberName like %:memberKeyword%  order by m.memberIdx desc")
    List<Member> findByMemberNameContaining(String memberKeyword);

}
