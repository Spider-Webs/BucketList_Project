package bucket.list.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UpdatePasswordDto {

    @NotBlank(message = "현재 비밀번호는 필수 입력 값입니다")
    private String currentPassword;
    @NotBlank(message = "새 비밀번호는 필수 입력 값입니다")
    private String updatePassword;
    @NotBlank(message = "새 비밀번호 확인은 필수 입력 값입니다")
    private String updatePasswordConfirm;



}
