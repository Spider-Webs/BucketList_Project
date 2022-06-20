package bucket.list.repository.Member;

import bucket.list.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    //중복확인을 위한 메서드
    Optional<Member> findByMemberId(String memberId);

    Optional<Member> findByMemberEmail(String memberEmail);
    @Modifying
    @Query(value = "update Member m set m.memberPassword = :memberPassword where m.memberIdx = :memberIdx")
    void updatePassword(Long memberIdx, String memberPassword);
}
