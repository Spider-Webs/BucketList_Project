package bucket.list.dto;

import bucket.list.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

//추가
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes = new HashMap<>();
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
        if("naver".equals(registrationId)){
            return ofNaver("id", attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .memberName((String) attributes.get("name"))
                .memberEmail((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .memberName((String) response.get("name"))
                .memberEmail((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .memberName(memberName)
                .memberEmail(memberEmail)
                .picture(picture)
                .role(Role.SOCIAL)
                .memberId("SNS_" + memberName)
                .build();
    }
}
