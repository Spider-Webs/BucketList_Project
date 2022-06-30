package bucket.list.service.Community;

import bucket.list.domain.Community;
import bucket.list.domain.CommunityComment;
import bucket.list.domain.Member;
import bucket.list.communitydto.CommunityCommentRequestDto;
import bucket.list.repository.Community.CommunityCommentRepository;
import bucket.list.repository.Community.CommunityRepository;
import bucket.list.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommunityCommentService {

    private final CommunityCommentRepository communityCommentRepository;
    private final MemberRepository memberRepository;
    private final CommunityRepository communityRepository;

    //댓글 저장
    @Transactional
    public Integer save(String memberId, int communityIdx, CommunityCommentRequestDto communityCommentRequestDto){

        Optional<Member> member = memberRepository.findByMemberId(memberId);

        Community community = communityRepository.findById(communityIdx).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 더 이상 존재하지 않습니다."));

        communityCommentRequestDto.setMember(member.get());
        communityCommentRequestDto.setCommunity(community);

        CommunityComment communityComment = communityCommentRequestDto.toEntity();
        communityCommentRepository.save(communityComment);

        return communityCommentRequestDto.getCommentIdx();
    }

    //댓글 수정
    @Transactional
    public void modify(int commentIdx, CommunityCommentRequestDto communityCommentRequestDto){
        CommunityComment comment = communityCommentRepository.findById(commentIdx).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 더 이상 존재하지 않습니다"));

        comment.modifyComment(communityCommentRequestDto.getCommentText());

        communityCommentRepository.save(comment);
    }

    //댓글 삭제
    @Transactional
    public void delete(int commentIdx){
        CommunityComment comment = communityCommentRepository.findById(commentIdx).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 더 이상 존재하지 않습니다"));

        communityCommentRepository.delete(comment);
    }

}
