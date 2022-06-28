package bucket.list.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


@Getter
@Setter
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerIdx;

    private String customerSubject;

    private String customerText;

    @ManyToOne
    @JoinColumn(name = "member_idx")
    private Member member; // 작성자

    private LocalDate customerDate;

    private String customerFile;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String customerPassword;
    
    private String customerSecret;

    @PrePersist
    public void localCustomerDate(){
        this.customerDate = LocalDate.now();
    }


}


