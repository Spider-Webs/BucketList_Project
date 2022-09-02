package bucket.list.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Notice implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noticeNumber;

    private String noticeSubject;

    private String noticeText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member; // 작성자

    private LocalDate noticeDate;

    private String noticeFile;

    @PrePersist
    public void localAboutDate(){
        this.noticeDate = LocalDate.now();
    }
}
