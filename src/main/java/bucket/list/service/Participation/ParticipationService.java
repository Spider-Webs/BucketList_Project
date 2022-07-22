package bucket.list.service.Participation;

import bucket.list.domain.Community;
import bucket.list.domain.Customer;
import bucket.list.domain.Member;
import bucket.list.domain.Participation;
import bucket.list.participationdto.ParticipationCommentRequestDto;
import bucket.list.participationdto.ParticipationRequestDto;
import bucket.list.participationdto.ParticipationResponseDto;
import bucket.list.repository.Member.MemberRepository;
import bucket.list.repository.Participation.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ParticipationService {

    @Value("${file.dir}")
    private String fileDir;

    private final ParticipationRepository participationRepository;
    private final MemberRepository memberRepository;


    //글저장 메서드
    public void save(ParticipationRequestDto participationRequestDto, MultipartFile file, String memberId) throws IOException {
        log.info("memberId:{}",memberId);
        //파일의 저장경로를 위한 경로설정
        boolean noneFIle = file.isEmpty();
        //업로드한 파일이 있다면
        if(!noneFIle) {
            String fileName = uploadFile(file);
            participationRequestDto.setParticipationFile(fileName);
        }else{
            participationRequestDto.setParticipationFile(null);
        }
        memberInsert(participationRequestDto, memberId);
        Participation participation = participationRequestDto.toEntity();
        participationRepository.save(participation);
    }

    private String uploadFile(MultipartFile file) throws IOException {
        String path = fileDir; //파일을 저장할 경로 지정
        UUID uuid = UUID.randomUUID();// 파일명 중복을 방지하기위해 UUID 객체사용
        String fileName = uuid + "_" + file.getOriginalFilename(); //file 이름설정
        File saveFile = new File(path, fileName);
        file.transferTo(saveFile);
        return fileName;
    }

    private void memberInsert(ParticipationRequestDto participationRequestDto, String memberId) {
        Optional<Member> byMemberId = memberRepository.findByMemberId(memberId);
        log.info("byMemberId:{}",byMemberId.get().getMemberId());
        participationRequestDto.setMember(byMemberId.get());
    }
    //참여하기 페이지 참여하기 전체글 서비스
    @Transactional(readOnly = true)
    public Page<Participation> participationList(Pageable pageable) {
        Page<Participation> participation = participationRepository.findAll(pageable);
        return participation;
    }

    //index 페이지 참여하기 게시글 서비스
    public Page<Participation> mainParticipationList(Pageable pageable) {
        Page<Participation> participation = participationRepository.findAll(pageable);
        return participation;
    }

    //참여하기 상세게기슬 목록
    public ParticipationResponseDto findParticipation(Integer participationIdx) {
        Participation participation = participationRepository.findById(participationIdx).get();
        return new ParticipationResponseDto(participation);
    }

    //글삭제 메서드
    public void deleteParticipation(Integer participationIdx){
        participationRepository.deleteById(participationIdx);
    }

    //조회수 증가 메서드
    public int updateCount(int participationIdx){
        return  participationRepository.updateCount(participationIdx);
    }


    public Page<Participation> findParticipationList(String memberId,Pageable pageable){
        Optional<Member> member = memberRepository.findByMemberId(memberId);
        log.info("memberId={}, member={}",memberId,member.get().getMemberId());

        return participationRepository.findByMember(memberId,pageable);
    }

    //메인페이지에서,참여하기 태그 검색하기
    public List<Participation> search(String keyword){
        List<Participation> participationList = participationRepository.findByParticipationTagContaining(keyword);

        return participationList;
    }
    //마이페이지에서 내가작성한 글 검색하기
    public Page<Participation> myWriteSearch(String memberId, String keyword,Pageable pageable){
        Page<Participation> myWriteSearch = participationRepository.findByParticipationWriterAndParticipationSubjectContaining(memberId, keyword,pageable);

        return myWriteSearch;

    }

}
