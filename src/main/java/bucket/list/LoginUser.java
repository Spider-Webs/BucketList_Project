package bucket.list;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Target(ElementType.PARAMETER)의 경우 해당 어노테이션이 생성될 위치를 지정
//PARAMETER로 지정했으므로 메소드의 파라미터로만해당 객체 사용가능
@Target(ElementType.PARAMETER)
//@Retention 어노테이션으로 어느 시점까지 어노테이션의 메모리를 가져갈 지 설정
@Retention(RetentionPolicy.RUNTIME)

// @interface 이 파일을 어노테이션 클래스로 지정 LoginUser라는 어노테이션이 생성
public @interface LoginUser {
}
