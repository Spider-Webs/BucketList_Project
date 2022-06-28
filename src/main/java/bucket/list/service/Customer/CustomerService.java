package bucket.list.service.Customer;

import bucket.list.domain.About;
import bucket.list.domain.Customer;
import bucket.list.domain.Member;
import bucket.list.repository.Customer.CustomerRepository;

import bucket.list.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

//고객센터 서비스
@RequiredArgsConstructor
@Service
public class CustomerService {

    @Value("${file.dir}")
    private String fileDir;

    private final CustomerRepository customerRepository;
    private final MemberRepository memberRepository;

    //고객센터 게시글저장
    public void save(Customer customer, MultipartFile file,String memberId) throws IOException {

        boolean noneFIle = file.isEmpty();

        if(!noneFIle) {
            String fileName = uploadFile(file);
            customer.setCustomerFile(fileName);
            memberInsert(customer, memberId);
            customerRepository.save(customer);
        }else{
            customer.setCustomerFile(null);
            memberInsert(customer, memberId);
            customerRepository.save(customer);
        }
    }

    private void memberInsert(Customer customer, String memberId) {
        Optional<Member> byMemberId = memberRepository.findByMemberId(memberId);
        customer.setMember(byMemberId.get());
    }

    private String uploadFile(MultipartFile file) throws IOException {
        String path = fileDir;
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(path, fileName);
        file.transferTo(saveFile);
        return fileName;
    }

    //고객센터 전체게시글보여주는 곳
    @Transactional
    public Page<Customer> allContentList(Pageable pageable) {
        Page<Customer> page = customerRepository.findAll(pageable);
        return page;
    }

    //하나의 게시글읽는곳
    @Transactional
    public Customer  oneContentList(int customerIdx){
        Customer customer = customerRepository.findById(customerIdx).get();

        return customer;
    }
    //게시글 삭제 서비스
    @Transactional
    public void deleteContent(int customerIdx){
        customerRepository.deleteById(customerIdx);
    }

}
