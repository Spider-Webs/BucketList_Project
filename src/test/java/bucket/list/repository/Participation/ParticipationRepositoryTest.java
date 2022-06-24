package bucket.list.repository.Participation;

import bucket.list.domain.Participation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ParticipationRepositoryTest {

    @Autowired
    ParticipationRepository participationRepository;

    @Test
    public void 검색_테스트(){
        String writer = "고루";
        String subject = "마존";

        Participation participation = new Participation();
        participation.setParticipationWriter(writer);
        participation.setParticipationSubject(subject);

        participationRepository.save(participation);

        List<Participation> byParticipationWriterAndParticipationSubjectContaining = participationRepository.findByParticipationWriterAndParticipationSubjectContaining("고루", "마");

        for (Participation participation1 : byParticipationWriterAndParticipationSubjectContaining) {
            System.out.println("participation1.getParticipationWriter() = " + participation1.getParticipationWriter());
            System.out.println("participation1.getParticipationSubject() = " + participation1.getParticipationSubject());
        }

    }

}