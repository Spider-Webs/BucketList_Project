package bucket.list.service.about;

import bucket.list.domain.About;
import bucket.list.domain.Member;
import bucket.list.repository.Member.MemberRepository;
import bucket.list.repository.about.AboutRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AboutService {
    @Value("${file.dir}")
    private String fileDir;

    private final AboutRepository aboutRepository;
    private final MemberRepository memberRepository;


    //글작성
    @CacheEvict(value = "allContentList", allEntries = true)
    public void save(About about, MultipartFile file,String memberId) throws IOException {
        
        boolean noneFIle = file.isEmpty();

        if(!noneFIle) {
            String fileName = uploadFile(file);
            about.setAboutFile(fileName);
            memberInsert(about, memberId);
            aboutRepository.save(about);
        }else{
            memberInsert(about, memberId);
            about.setAboutFile(null);
            aboutRepository.save(about);
        }
        
    }

    private void memberInsert(About about, String memberId) {
        Optional<Member> byMemberId = memberRepository.findByMemberId(memberId);
        about.setMember(byMemberId.get());
    }

    //파일 업로드 메서드
    private String uploadFile(MultipartFile file) throws IOException {
        String path = fileDir;
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(path, fileName);
        file.transferTo(saveFile);
        return fileName;
    }

    //게시글리스트 처리, 최신글 정렬
    //페이징구현하기위해 Pageable을 매개변수입력
    @Cacheable(value ="allContentList")
    public Page<About> allContentList(Pageable pageable) {

        Page<About> page = aboutRepository.findAll(pageable);
        return page;
    }

    //특정게시글 보는 메서드
    public About oneContentList(Integer aboutNumber) {

        About about = aboutRepository.findById(aboutNumber).get();

        return about;
    }

    @CacheEvict(value = "allContentList", allEntries = true)
    //글삭제메서드
    public void deleteContent(Integer aboutNumber){
        aboutRepository.deleteById(aboutNumber);
    }


}
