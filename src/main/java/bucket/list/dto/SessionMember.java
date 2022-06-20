package bucket.list.dto;

import bucket.list.domain.Member;

import java.io.Serializable;

public class SessionMember implements Serializable {

    private String memberName;
    private String memberEmail;
    private String picture;

    public SessionMember(Member member) {
        this.memberName = member.getMemberName();
        this.memberEmail = member.getMemberEmail();
        this.picture = member.getPicture();
    }
}
