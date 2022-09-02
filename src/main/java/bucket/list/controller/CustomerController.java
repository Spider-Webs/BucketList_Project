package bucket.list.controller;


import bucket.list.config.LoginUser;
import bucket.list.domain.Customer;
import bucket.list.dto.CustomerDto;
import bucket.list.memberdto.SessionMember;
import bucket.list.service.Customer.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;


    //전체 게시글 리스트 보여주는 곳
    @GetMapping
    public String customer(Model model,@PageableDefault(page = 0, size = 10, sort = "customerIdx",direction = Sort.Direction.DESC) Pageable pageable){

        Page<Customer> customerItems = customerService.CustomerList(pageable);

        //현재 페이지 변수 Pageable 0페이지부터 시작하기 +1을해줘서 1페이지부터 반영한다
        int nowPage = customerItems.getPageable().getPageNumber() +1;
        //블럭에서 보여줄 시작페이지(Math.max 한이유는 시작페이지가 마이너스 값일 수는 업으니깐 Math.max를 사용)
        int startPage =Math.max(nowPage-4,1) ;
        //블럭에서 보여줄때 마지막페이지(Math.min 한이유는 총페이지가 10페이지인데, 현재페이지가 9페이지이면 14페이지가되므로 오류,
        //그렇기에 getTotalpage를  min으로설정)
        int endPage = Math.min(nowPage + 5,  customerItems.getTotalPages()) ;

        model.addAttribute("customerItems", customerItems);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "/customer/main";
    }

    //고객센터 글작성 보여주는 메서드
    @GetMapping("/write")
    public String writeForm(Model model){

        model.addAttribute("customer", new Customer());

        return "/customer/write";
    }
    //고객센터 글작성폼에서 넘어오는 메서드
    @PostMapping("/write")
    public String writeForm(@Valid  CustomerDto customerDto, BindingResult bindingResult, @LoginUser SessionMember sessionMember, MultipartFile file) throws IOException {

        if(bindingResult.hasErrors()){

            return "customer/write";
        }
        customerService.save(customerDto, file,sessionMember.getMemberId());
        return "redirect:/customer";
    }

    //고객센터 게시글 클릭 후 비밀번호 페이지 이동
    @GetMapping("/{customerIdx}/read")
    public String readOrSecretRead(@PathVariable int customerIdx, Model model){

        model.addAttribute("customerIdx", customerIdx);
        return "/customer/secretRead";

    }
    //비밀글에서 비밀번호 입력
    @PostMapping("/{customerIdx}/read")
    public String secretReadConfirm(@RequestParam("customerPassword")String customerPassword,
                                    @PathVariable("customerIdx")int customerIdx,Model model,
                                    @LoginUser SessionMember sessionMember){

        Customer customer = customerService.findCustomer(customerIdx);

        if(customer.getCustomerPassword().equals(customerPassword)){
            log.info("customerId = {},sessionId = {}",customer.getMember().getMemberId(),sessionMember.getMemberId());
            model.addAttribute("sessionMember", sessionMember.getMemberId());
            model.addAttribute("customer", customer);
            return "/customer/read";
        }else{
            return "customer/readFail";
        }
    }

    //글수정 폼으로 이동하는 메서드
    @GetMapping("/edit/{customerIdx}")
    public String editForm(@PathVariable int customerIdx, Model model){
        Customer customer = customerService.findCustomer(customerIdx);
        model.addAttribute("customer", customer);
        model.addAttribute("customerIdx", customerIdx);
        return "customer/edit";
    }

    @PostMapping("/edit/{customerIdx}")
    //실제 게시글수정, 파일이미지 업로드
    public String edit(@ModelAttribute("customer") CustomerDto customerDto, @PathVariable int customerIdx, MultipartFile file,@LoginUser SessionMember sessionMember) throws IOException {
        customerService.save(customerDto,file,sessionMember.getMemberId());
        return "redirect:/customer/";
    }

    //고객센터 글삭제 컨트롤러
    //게시글 삭제
    @GetMapping("/delete/{customerIdx}")
    public String delete(@PathVariable int customerIdx){
        customerService.deleteCustomer(customerIdx);
        return "redirect:/customer";
    }

}
