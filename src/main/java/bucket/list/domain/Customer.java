package bucket.list.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerIdx;

    private String customerSubject;

    private String customerText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member; // 작성자

    private LocalDate customerDate;

    private String customerFile;

    private String customerPassword;
    
    private String customerSecret;

    @PrePersist
    public void localCustomerDate(){
        this.customerDate = LocalDate.now();
    }


}


