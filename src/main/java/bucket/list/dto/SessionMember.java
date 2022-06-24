package bucket.list.dto;

import bucket.list.domain.Member;
import bucket.list.domain.Role;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {

    private String memberId;
    private String memberName;
    private String memberEmail;
    private String memberPassword;
    private Role role;


    public SessionMember(Member member) {
        this.memberName = member.getMemberName();
        this.memberEmail = member.getMemberEmail();
        this.role = member.getRole();
        this.memberId = member.getMemberId();
        this.memberPassword = member.getMemberPassword();
    }


}
