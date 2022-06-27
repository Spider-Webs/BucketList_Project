package bucket.list.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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
    private String participationWriter;
    private String participationSubject;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer participationCount;

    @OneToMany(mappedBy = "participation", cascade = CascadeType.REMOVE)
    private List<ParticipationComment> comments = new ArrayList<>();

    @PrePersist
    public void localAboutDate(){
        this.participationDate = LocalDate.now();
    }
    

}
