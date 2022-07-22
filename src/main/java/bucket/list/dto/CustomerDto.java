package bucket.list.dto;

import bucket.list.domain.Customer;
import bucket.list.domain.Member;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CustomerDto {

    private Integer customerIdx;
    private String customerSubject;
    private String customerText;
    private Member member; // 작성자
    private String customerFile;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String customerPassword;
    private String customerSecret;

    /*Dto -> toEntity*/
    public Customer toEntity(){
        Customer customer = Customer.builder()
                .customerIdx(customerIdx)
                .customerSubject(customerSubject)
                .customerText(customerText)
                .member(member)
                .customerFile(customerFile)
                .customerPassword(customerPassword)
                .customerSecret(customerSecret)
                .build();

        return customer;

    }

}
