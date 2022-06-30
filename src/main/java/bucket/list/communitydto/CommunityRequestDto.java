package bucket.list.communitydto;

import bucket.list.domain.Community;
import bucket.list.domain.Member;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CommunityRequestDto {

    private Integer communityIdx;
    private String communitySubject;
    private String communityTag;
    private String communityOption;
    private String communityText;
    private String communityFile;
    private Member member;
    private Integer communityCount;
    private LocalDate communityDate;

    public Community toEntity(){
        Community community = Community.builder()
                .communityIdx(communityIdx)
                .communitySubject(communitySubject)
                .communityTag(communityTag)
                .communityOption(communityOption)
                .communityText(communityText)
                .communityFile(communityFile)
                .member(member)
                .communityCount(0)
                .communityDate(communityDate)
                .build();

        return community;
    }
}
