package bucket.list.dto;

import bucket.list.domain.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
public class SecurityMember extends User {

    private Member member;

    public SecurityMember(Member member){
        super(member.getMemberId(), member.getMemberPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()) );

        this.member = member;
    }

}
