package bucket.list.domain;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private String communityWriter;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer communityCount;

    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE)
    private List<CommunityComment> comments = new ArrayList<>();


    @PrePersist
    public void localAboutDate(){
        this.communityDate = LocalDate.now();
    }


}
