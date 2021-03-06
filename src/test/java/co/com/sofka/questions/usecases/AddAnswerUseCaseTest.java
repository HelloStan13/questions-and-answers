package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class AddAnswerUseCaseTest {

    @SpyBean
    private AddAnswerUseCase addAnswerUseCase;

    @SpyBean
    private GetUseCase getUseCase;

    @Autowired
    private MapperUtils mapperUtils;

    @MockBean
    AnswerRepository answerRepository;

    @Test
    void addAnswerHappyPass() {
        Question question = new Question();
        QuestionDTO questionDTO = new QuestionDTO("12334", "asdf", "Cuanto es 2 + 2", "Matematicas", "Operaciones");
        question.setId(questionDTO.getId());
        question.setUserId(questionDTO.getUserId());
        question.setQuestion(questionDTO.getQuestion());
        question.setType(questionDTO.getType());
        question.setCategory(questionDTO.getCategory());

        List<AnswerDTO> answersDTO = new ArrayList<>();

        Answer answer = new Answer();
        AnswerDTO answerDTO = new AnswerDTO("4654665fgf", "12aa334", "564b6fb", "que es java", 4, LocalDateTime.of(2022, 06, 10, 8, 0, 0));
        answer.setQuestionId(answerDTO.getQuestionId());
        answer.setUserId(answerDTO.getUserId());
        answer.setAnswer(answerDTO.getAnswer());
        answer.setCreateAt(answerDTO.getCreateAt());

        answersDTO.add(answerDTO);

        questionDTO.setAnswers(answersDTO);

        Mockito.when(getUseCase.apply(answerDTO.getQuestionId())).thenReturn(Mono.just(questionDTO));
        Mockito.when(answerRepository.save(any())).thenReturn(Mono.just(mapperUtils.mapperToAnswer(answerDTO.getId()).apply(answerDTO)));

        var result= addAnswerUseCase.apply(answerDTO);

        StepVerifier
                .create(result)
                .expectNext(questionDTO)
                .verifyComplete();
    }
}