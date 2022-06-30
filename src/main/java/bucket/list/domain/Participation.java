package bucket.list.domain;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer participationIdx;
    private String participationOption;
    private String participationTag;
    private String participationText;
    private String participationFile;
    private LocalDate participationDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member; // 작성자
    private String participationSubject;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer participationCount;//조회수 확인하기위한 변수 기본값은 0, null허용불가 설정

    @OneToMany(mappedBy = "participation", cascade = CascadeType.REMOVE)//REMOVE 설정이유는 게시글 삭제시 해당 게시글에대한 댓글도 같이 삭제시키기위함
    @OrderBy(value = "comment_idx DESC" )//최신 댓글부터 보기위해, 기본키 내림차순으로 정렬
    private List<ParticipationComment> comments = new ArrayList<>();

    @PrePersist
    public void localAboutDate(){
        this.participationDate = LocalDate.now();
    } //insert 메서드 호출전 DATE변수에 오늘날짜 대입
    

}
