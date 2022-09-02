package bucket.list.participationdto;

import bucket.list.domain.Member;
import bucket.list.domain.Participation;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
/*
게시글 작성 후 작성 요청을 처리할 요청 클래스
서비스계층에서 Entity 에 저장
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ParticipationRequestDto {

    private Integer participationIdx;
    private String participationOption;
    private String participationTag;
    private String participationText;
    private String participationFile;
    private LocalDate participationDate;
    private Member member;
    private String participationSubject;
    private Integer participationCount;

    /*
    Dto->Entity
     */
    public Participation toEntity(){
        Participation participation = Participation.builder()
                .participationIdx(participationIdx)
                .participationOption(participationOption)
                .participationTag(participationTag)
                .participationText(participationText)
                .participationFile(participationFile)
                .participationDate(participationDate)
                .member(member)
                .participationSubject(participationSubject)
                .participationCount(0)
                .build();

        return participation;

    }


}
