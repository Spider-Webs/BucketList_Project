package bucket.list.service.Participation;

import bucket.list.domain.Community;
import bucket.list.domain.Participation;
import bucket.list.repository.Participation.ParticipationRepository;
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
import java.util.UUID;

@Service
@Transactional
public class ParticipationService {

    @Value("${file.dir}")
    private String fileDir;

    private final ParticipationRepository participationRepository;

    @Autowired
    public ParticipationService(ParticipationRepository participationRepository) {
        this.participationRepository = participationRepository;
    }

    //글저장 메서드

    public void save(Participation participation, MultipartFile file) throws IOException {

        //파일의 저장경로를 위한 경로설정
        boolean noneFIle = file.isEmpty();
        //업로드한 파일이 있다면
        if(!noneFIle) {
            String path = fileDir;

            //이미지파일 중복을 방지하기위해 uuid설정
            UUID uuid = UUID.randomUUID();

            //filename의 uuid + 파일명
            String fileName = uuid + "_" + file.getOriginalFilename();


            File saveFile = new File(path, fileName);

            //파일전송
            file.transferTo(saveFile);

            participation.setParticipationFile(fileName);


            participationRepository.save(participation);
        }else{
            participation.setParticipationFile(null);
            participationRepository.save(participation);
        }
    }
    public Page<Participation> AllContentList(Pageable pageable) {
        Page<Participation> participation = participationRepository.findAll(pageable);
        return participation;
    }

    // 메인 게시글을 위한 코드
    public Page<Participation> MainAllContentList(Pageable pageable2) {
        Page<Participation> participation = participationRepository.findAll(pageable2);
        return participation;
    }



    //하나의 게시글
    @Transactional
    public Participation oneContentList(Integer participationIdx) {
        Participation participation = participationRepository.findById(participationIdx).get();
        return participation;
    }

    //글삭제 메서드
    @Transactional
    public void deleteContent(Integer participationIdx){
        participationRepository.deleteById(participationIdx);
    }

    //조회수 증가 메서드
    @Transactional
    public int updateCount(int participationIdx){


        return  participationRepository.updateCount(participationIdx);

    }

    @Transactional
    public String findWriter(int participationIdx){
        return participationRepository.findWriter(participationIdx);
    }

    public List<Participation> findAllWriteList(String participationWriter){
        return participationRepository.findAllWriteList(participationWriter);
    }

    //메인페이지에서,참여하기 태그 검색하기
    @Transactional
    public List<Participation> search(String keyword){
        List<Participation> participationList = participationRepository.findByParticipationTagContaining(keyword);

        return participationList;
    }
    //마이페이지에서 내가작성한 글 검색하기
    @Transactional
    public List<Participation> myWriteSearch(String participationWriter, String keyword){
        List<Participation> myWriteSearch = participationRepository.findByParticipationWriterAndParticipationSubjectContaining(participationWriter, keyword);

        return myWriteSearch;

    }

}
