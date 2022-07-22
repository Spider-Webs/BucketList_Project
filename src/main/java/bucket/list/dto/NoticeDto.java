package bucket.list.dto;

import bucket.list.domain.Notice;
import bucket.list.domain.Member;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDto {

    private Integer noticeNumber;
    @NotNull(message = "제목이 입력되어있지 않습니다. 제목을 입력해주세요")
    private String noticeSubject;
    @NotNull(message = "글 내용이 입력되어있지 않습니다. 글을 입력해주세요")
    private String noticeText;
    private Member member;
    private String noticeFile;

    /*Dto -> toEntity*/
    public Notice toEntity(){
        Notice notice = Notice.builder()
                .noticeNumber(this.noticeNumber)
                .noticeSubject(this.noticeSubject)
                .noticeText(this.noticeText)
                .member(this.member)
                .noticeFile(this.noticeFile)
                .build();

        return notice;
    }


}
