package bucket.list.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class About implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer aboutNumber;

    private String aboutSubject;

    private String aboutText;

    @ManyToOne
    @JoinColumn(name = "member_idx")
    private Member member; // 작성자

    private LocalDate aboutDate;

    private String aboutFile;


    @PrePersist
    public void localAboutDate(){
        this.aboutDate = LocalDate.now();
    }

}
