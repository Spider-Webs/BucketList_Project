package bucket.list.service.Community;

import bucket.list.domain.Community;

import bucket.list.domain.Participation;
import bucket.list.repository.Community.CommunityRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service     //spring bean 사용하여 따로 설정했으므로 삭제해야함
public class CommunityService {

    @Value("${file.dir}")
    private String fileDir;

    private final CommunityRepository communityRepository;



    public void createCommunity(Community community, MultipartFile file) throws IOException {             // 작성

        //파일의 저장경로를 위한 경로설정
        boolean noneFIle = file.isEmpty();

        if (!noneFIle) {
            String path = fileDir;

            //이미지파일 중복을 방지하기위해 uuid설정
            UUID uuid = UUID.randomUUID();

            //filename의 uuid + 파일명
            String fileName = uuid + "_" + file.getOriginalFilename();


            File saveFile = new File(path, fileName);

            //파일전송
            file.transferTo(saveFile);

            community.setCommunityFile(fileName);

            communityRepository.save(community);
        } else {
            community.setCommunityFile(null);
            communityRepository.save(community);
        }

    }

    public Community oneContentList(int communityIdx){       // 해당 게시물 출력
        return communityRepository.findById(communityIdx).get();
    }

    public Page<Community> allContentList(Pageable pageable){      // 전체 게시물 출력
        Page<Community> communities = communityRepository.findAll(pageable);
        return communities;            // repository에 findAll 함수 호출
    }



    public Page<Community> findAllWriteList(String communityWriter,Pageable pageable){       // 아이디를 통해 나머지 정보 구하기
        return communityRepository.findAllWriteList(communityWriter,pageable);
    }

    public String findWriter(int communityIdx){
        return communityRepository.findWriter(communityIdx);
    }

    public void communityDelete(int communityIdx){           // 게시물 삭제
        communityRepository.deleteById(communityIdx);
    }

    public int updateCount(int communityIdx){


        return  communityRepository.updateCount(communityIdx);

    }
    //마이페이지에서 내가작성한 글 검색하기
    public Page<Community> myWriteSearch(String communityWriter, String keyword,Pageable pageable){
        Page<Community> myWriteSearch = communityRepository.findByCommunityWriterAndCommunitySubjectContaining(communityWriter, keyword,pageable);

        return myWriteSearch;

    }


}
