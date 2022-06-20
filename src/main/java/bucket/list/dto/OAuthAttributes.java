package bucket.list.dto;

import bucket.list.domain.Member;
import bucket.list.domain.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

//추가
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String memberName;
    private String memberEmail;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey, String memberName,
                           String memberEmail, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey= nameAttributeKey;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .memberName((String) attributes.get("memberName"))
                .memberEmail((String) attributes.get("memberEmail"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }


    public Member toEntity() {
        return Member.builder()
                .memberName(memberName)
                .memberEmail(memberEmail)
                .picture(picture)
                .role(Role.USER)
                .build();
    }
}
