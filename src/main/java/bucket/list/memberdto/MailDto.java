package bucket.list.memberdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//임시비밀번호 발송을위한 dto
@Getter
@Setter
@NoArgsConstructor
public class MailDto {

    private String address;
    private String title;
    private String message;

}
