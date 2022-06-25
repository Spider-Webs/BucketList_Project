package bucket.list.service.Participation;

import bucket.list.domain.ParticipationComment;
import bucket.list.repository.Participation.ParticipationCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ParticipationCommentService {

    private final ParticipationCommentRepository participationCommentRepository;


    @Transactional
    public void save(ParticipationComment participationComment){
          participationCommentRepository.save(participationComment);
    }


    @Transactional
    public List<ParticipationComment> allContentList(int commentNumber){
        List<ParticipationComment> participationComments = participationCommentRepository.allContentList(commentNumber);
        return participationComments;
    }

    //댓글삭제메서드
    @Transactional
    public void deleteComment(int commentIdx){
        participationCommentRepository.deleteById(commentIdx);

    }
    //작성자
    @Transactional
    public String findCommentWriter(int commentIdx){
        return participationCommentRepository.findCommentWriter(commentIdx);
    }
}
