package bucket.list.participationdto;

import bucket.list.communitydto.CommunityCommentResponseDto;
import bucket.list.domain.Member;
import bucket.list.domain.Participation;
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
public class ParticipationResponseDto {

    private Integer participationIdx;
    private String participationOption;
    private String participationTag;
    private String participationText;
    private String participationFile;
    private LocalDate participationDate;
    private Member member;
    private String participationSubject;
    private Integer participationCount;
    private List<ParticipationCommentResponseDto> comments = new ArrayList<>();
    /*
    Entity ->Dto
     */
    public ParticipationResponseDto(Participation participation){
        this.participationIdx = participation.getParticipationIdx();
        this.participationOption = participation.getParticipationOption();
        this.participationTag = participation.getParticipationTag();
        this.participationText = participation.getParticipationText();
        this.participationFile = participation.getParticipationFile();
        this.participationDate = participation.getParticipationDate();
        this.member = participation.getMember();
        this.participationSubject = participation.getParticipationSubject();
        this.participationCount = participation.getParticipationCount();
        this.comments = participation.getComments().stream().map(ParticipationCommentResponseDto::new).collect(Collectors.toList());
    }
}
