package bucket.list.dto;

import bucket.list.domain.About;
import bucket.list.domain.Member;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AboutDto {

    private Integer aboutNumber;
    private String aboutSubject;
    private String aboutText;
    private Member member;
    private LocalDate aboutDate;
    private String aboutFile;

    /*Dto -> toEntity*/
    @Builder
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

    public void fileExist(MultipartFile file){
        if(file.isEmpty()){
//            aboutFile
        }
    }

}
