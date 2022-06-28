package bucket.list.domain;

import bucket.list.dto.AddressEmbed;
import bucket.list.dto.MemberFormDto;
import bucket.list.dto.UpdatePasswordDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Setter
@Getter
@Entity
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


        member.setRole(Role.USER);

        return member;
    }



}
