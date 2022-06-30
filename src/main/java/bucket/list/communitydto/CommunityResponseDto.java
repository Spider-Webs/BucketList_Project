package bucket.list.communitydto;

import bucket.list.domain.Community;
import bucket.list.domain.Member;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
public class CommunityResponseDto {
    private Integer communityIdx;
    private String communitySubject;
    private String communityTag;
    private String communityOption;
    private String communityText;
    private String communityFile;
    private Member member;
    private Integer communityCount;
    private LocalDate communityDate;
    private List<CommunityCommentResponseDto> comments = new ArrayList<>();

    public CommunityResponseDto(Community community){
        this.communityIdx = community.getCommunityIdx();
        this.communitySubject = community.getCommunitySubject();
        this.communityTag = community.getCommunityTag();
        this.communityOption = community.getCommunityOption();
        this.communityText = community.getCommunityText();
        this.member = community.getMember();
        this.communityCount = community.getCommunityCount();
        this.communityFile = community.getCommunityFile();
        this.communityDate = community.getCommunityDate();
        this.comments = community.getComments().stream().map(CommunityCommentResponseDto::new).collect(Collectors.toList());
    }

}
