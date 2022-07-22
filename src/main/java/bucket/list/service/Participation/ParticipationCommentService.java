package bucket.list.service.Participation;

import bucket.list.domain.*;
import bucket.list.participationdto.ParticipationCommentRequestDto;
import bucket.list.repository.Member.MemberRepository;
import bucket.list.repository.Participation.ParticipationCommentRepository;
import bucket.list.repository.Participation.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ParticipationCommentService {

    private final ParticipationCommentRepository participationCommentRepository;
    private final ParticipationRepository participationRepository;
    private final MemberRepository memberRepository;

    //댓글 저장
    @Transactional
    public Integer save(String memberId, int participationIdx, ParticipationCommentRequestDto participationComment){

        Optional<Member> member = memberRepository.findByMemberId(memberId);

        Participation participation = participationRepository.findById(participationIdx).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 더 이상 존재하지 않습니다."));

        participationComment.setMember(member.get());
        participationComment.setParticipation(participation);

        ParticipationComment comment = participationComment.toEntity();
        participationCommentRepository.save(comment);

        return participationComment.getCommentIdx();
    }

    //댓글 수정
    @Transactional
    public void modify(int participationIdx, ParticipationCommentRequestDto participationComment){
        ParticipationComment comment = participationCommentRepository.findById(participationIdx).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 더 이상 존재하지 않습니다"));

        comment.modifyComment(participationComment.getCommentText());
        participationCommentRepository.save(comment);
    }

    //댓글 삭제
    @Transactional
    public void delete(int commentIdx){
        ParticipationComment comment = participationCommentRepository.findById(commentIdx).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 더 이상 존재하지 않습니다"));

        participationCommentRepository.delete(comment);
    }    
}
