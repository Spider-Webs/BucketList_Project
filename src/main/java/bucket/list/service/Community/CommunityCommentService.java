package bucket.list.service.Community;

import bucket.list.domain.Community;
import bucket.list.domain.CommunityComment;
import bucket.list.domain.Member;
import bucket.list.domain.ParticipationComment;
import bucket.list.repository.Community.CommunityCommentRepository;
import bucket.list.repository.Community.CommunityRepository;
import bucket.list.repository.Member.MemberRepository;
import bucket.list.repository.Participation.ParticipationCommentRepository;
import bucket.list.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommunityCommentService {

    private final CommunityCommentRepository communityCommentRepository;
    private final MemberRepository memberRepository;
    private final CommunityRepository communityRepository;


    @Transactional
    public int save(String memberId,int communityIdx,CommunityComment communityComment){

        Optional<Member> member = memberRepository.findByMemberId(memberId);

        Community community = communityRepository.findById(communityIdx).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 더 이상 존재하지 않습니다."));

        communityComment.setMember(member.get());
        communityComment.setCommunity(community);

        communityCommentRepository.save(communityComment);

        return communityComment.getCommentIdx();
    }


//    @Transactional
//    public List<CommunityComment> allContentList(int commentNumber){
//        List<CommunityComment> communityComments = communityCommentRepository.allContentList(commentNumber);
//        return communityComments;
//    }

    //댓글삭제메서드
    @Transactional
    public void deleteComment(int commentIdx){
        communityCommentRepository.deleteById(commentIdx);

    }
    //작성자
//    @Transactional
//    public String findCommentWriter(int commentIdx){
//        return communityCommentRepository.findCommentWriter(commentIdx);
//    }
}
