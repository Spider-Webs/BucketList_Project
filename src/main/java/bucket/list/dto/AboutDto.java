package bucket.list.dto;

import bucket.list.domain.About;
import bucket.list.domain.Member;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AboutDto {

    private Integer aboutNumber;
    private String aboutSubject;
    private String aboutText;
    private Member member;
    private String aboutFile;

    /*Dto -> toEntity*/
    public About toEntity(){
        About about = About.builder()
                .aboutNumber(aboutNumber)
                .aboutSubject(aboutSubject)
                .aboutText(aboutText)
                .member(member)
                .aboutFile(aboutFile)
                .build();

        return about;
    }


}
