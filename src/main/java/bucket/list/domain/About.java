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
public class About implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer aboutNumber;

    private String aboutSubject;

    private String aboutText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member; // 작성자

    private LocalDate aboutDate;

    private String aboutFile;

    @PrePersist
    public void localAboutDate(){
        this.aboutDate = LocalDate.now();
    }

}
