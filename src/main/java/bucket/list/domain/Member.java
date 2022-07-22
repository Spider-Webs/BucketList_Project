package bucket.list.domain;

import bucket.list.memberdto.AddressEmbed;
import bucket.list.memberdto.MemberFormDto;
import bucket.list.memberdto.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(indexes = @Index(name = "member_index", columnList = "member_name"))
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_idx")
    private Long memberIdx;

    @Column(name="member_id")
    private String memberId;

    @Column(name="member_name")
    private String memberName;

    @Column(name="member_password")
    private String memberPassword;

    @Column(name="member_email")
    private String memberEmail;
    @Column(name = "member_phone")
    private String memberPhone;
    @Embedded
    private AddressEmbed addressEmbed;
    @Column
    //추가
    private String picture;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Member(){

    }

    @Builder
    public Member(String memberName,String memberEmail, String picture, Role role,String memberId){
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.picture = picture;
        this.role = role;
        this.memberId = memberId;
    }

    public Member update(String memberName, String picture){
        this.memberName = memberName;
        this.picture = picture;

        return this;
    }

    public static Member createMember(MemberFormDto memberFormDto,PasswordEncoder passwordEncoder){
        Member member = new Member();
        AddressEmbed addressEmbed = new AddressEmbed(memberFormDto.getZipcode(), memberFormDto.getStreetAdr(), memberFormDto.getDetailAdr());
        member.setMemberId(memberFormDto.getMemberId());
        member.setMemberName(memberFormDto.getMemberName());
        member.setMemberEmail(memberFormDto.getMemberEmail());
        member.setMemberPhone(memberFormDto.getMemberPhone());
        member.setAddressEmbed(addressEmbed);
        String password = passwordEncoder.encode(memberFormDto.getMemberPassword());
        member.setMemberPassword(password);
        member.setRole(Role.ADMIN);

        return member;
    }


}
