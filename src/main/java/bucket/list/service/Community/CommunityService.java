package bucket.list.service.Community;

import bucket.list.domain.Community;

import bucket.list.domain.Member;
import bucket.list.communitydto.CommunityRequestDto;
import bucket.list.communitydto.CommunityResponseDto;
import bucket.list.repository.Community.CommunityRepository;
import bucket.list.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommunityService {

    @Value("${file.dir}")
    private String fileDir;

    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;

    public void save(CommunityRequestDto communityRequestDto, MultipartFile file, String memberId) throws IOException {             // 작성
        boolean noneFIle = file.isEmpty();
        //파일의 저장경로를 위한 경로설정
        if(!noneFIle) {
            String fileName = uploadFile(file);
            communityRequestDto.setCommunityFile(fileName);
        } else {
            communityRequestDto.setCommunityFile(null);
        }
        memberInsert(communityRequestDto, memberId);
        Community community = communityRequestDto.toEntity();
        communityRepository.save(community);
    }

    private String uploadFile(MultipartFile file) throws IOException {
        String path = fileDir; //파일을 저장할 경로 지정
        UUID uuid = UUID.randomUUID();// 파일명 중복을 방지하기위해 UUID 객체사용
        String fileName = uuid + "_" + file.getOriginalFilename(); //file 이름설정
        File saveFile = new File(path, fileName);
        file.transferTo(saveFile);
        return fileName;
    }

    private void memberInsert(CommunityRequestDto communityRequestDto, String memberId) {
        Optional<Member> byMemberId = memberRepository.findByMemberId(memberId);
        log.info("byMemberId:{}",byMemberId.get().getMemberId());
        communityRequestDto.setMember(byMemberId.get());
    }

    public CommunityResponseDto findCommunity(int communityIdx){

        Community community = communityRepository.findById(communityIdx).get();// 해당 게시물 출력

        return new CommunityResponseDto(community);
    }
    @Transactional(readOnly = true)
    public Page<Community> CommunityList(Pageable pageable){      // 전체 게시물 출력
        Page<Community> communities = communityRepository.findAll(pageable);
        return communities;
    }

    public Page<Community> findCommunityList(String communityWriter,Pageable pageable){
        return communityRepository.findAllWriteList(communityWriter,pageable);
    }

    public void communityDelete(int communityIdx){           // 게시물 삭제
        communityRepository.deleteById(communityIdx);
    }

    public int updateCount(int communityIdx){
        return  communityRepository.updateCount(communityIdx);
    }
    //마이페이지에서 내가작성한 글 검색하기
    public Page<Community> myWriteSearch(String memberId, String keyword,Pageable pageable){
        Page<Community> myWriteSearch = communityRepository.findByCommunityWriterAndCommunitySubjectContaining(memberId, keyword,pageable);

        return myWriteSearch;

    }


}
