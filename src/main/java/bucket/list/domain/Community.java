package bucket.list.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer communityIdx;            // 게시물 번호 (기본키값)
    private String communitySubject;
    private String communityTag;
    private String communityOption;
    private String communityText;
    private LocalDate communityDate;
    private String communityFile;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member; // 작성자

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer communityCount;//조회수 확인하기위한 변수 기본값은 0, null허용불가 설정

    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE)//REMOVE 설정이유는 게시글 삭제시 해당 게시글에대한 댓글도 같이 삭제시키기위함
    @OrderBy(value = "comment_idx DESC" )//최신 댓글부터 보기위해, 기본키 내림차순으로 정렬
    private List<CommunityComment> comments = new ArrayList<>();

    @PrePersist
    public void localAboutDate(){
        this.communityDate = LocalDate.now();
    }//insert 메서드 호출전 DATE변수에 오늘날짜 대입



}
