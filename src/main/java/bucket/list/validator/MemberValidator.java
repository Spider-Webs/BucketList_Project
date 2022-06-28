package bucket.list.validator;


import bucket.list.dto.UpdatePasswordDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
//비밀번호와 비밀번호 확인이 일치한지 확인하기위한 validator 선언
public class MemberValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UpdatePasswordDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UpdatePasswordDto updatePasswordDto = (UpdatePasswordDto) target;

        if(updatePasswordDto.getUpdatePassword().equals(updatePasswordDto.getUpdatePasswordConfirm())==false){
            errors.rejectValue("updatePassword","NotEquals","비밀번호와 비밀번호 확인이 일치하지 않습니다");
            errors.rejectValue("updatePasswordConfirm","NotEquals","비밀번호와 비밀번호 확인이 일치하지 않습니다");
        }

    }
}
