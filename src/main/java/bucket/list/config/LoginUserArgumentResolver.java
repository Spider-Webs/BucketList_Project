package bucket.list.config;

import bucket.list.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    //컨트롤러의 메서드의 특정 파라미터를 지원하는지 판단
    //@LoginUser 어노테이션이 붙어있고,SessionMember.class 이면 true를 반환
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean isLoginUser = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isMemberClass = SessionMember.class.equals(parameter.getParameterType());


        return isLoginUser&&isMemberClass;
    }

    //파라미터에 전달할 객체생성
    //현재 세션에서 객체를 가져온다
    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {



        return httpSession.getAttribute("member");
    }
}
