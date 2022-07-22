package bucket.list.service.notice;

import bucket.list.domain.Notice;
import bucket.list.domain.Member;
import bucket.list.dto.NoticeDto;
import bucket.list.repository.Member.MemberRepository;
import bucket.list.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class NoticeService {
    @Value("${file.dir}")
    private String fileDir;

    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;

    //글작성
    @CacheEvict(value = "allContentList", allEntries = true)
    public void save(NoticeDto dto, MultipartFile file, String memberId) throws IOException {
        
        boolean noneFIle = file.isEmpty();

        if(!noneFIle) {
            log.info("text:{}",dto.getNoticeText());
            String fileName = uploadFile(file);
            dto.setNoticeFile(fileName);
        }
        else{
            dto.setNoticeFile(null);
        }
        memberInsert(dto, memberId);
        Notice notice = dto.toEntity();
        noticeRepository.save(notice);
    }

    private void memberInsert(NoticeDto dto, String memberId) {
        Optional<Member> byMemberId = memberRepository.findByMemberId(memberId);
        dto.setMember(byMemberId.get());
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
    @Transactional(readOnly = true)
    public Page<Notice> noticeList(Pageable pageable) {

        Page<Notice> page = noticeRepository.findAll(pageable);

        return page;
    }


    //특정게시글 보는 메서드
    public Notice findNotice(Integer noticeNumber) {

        Notice notice = noticeRepository.findById(noticeNumber).get();

        return notice;
    }

    @CacheEvict(value = "allContentList", allEntries = true)
    //글삭제메서드
    public void deleteNotice(Integer noticeNumber){
        noticeRepository.deleteById(noticeNumber);
    }

}
