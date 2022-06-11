package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@SpringBootTest
class GetUseCaseTest {

    private GetUseCase getUseCase;

    @Autowired
    private MapperUtils mapperUtils;

    @Mock
    QuestionRepository questionRepository;

    @Mock
    AnswerRepository answerRepository;

    @BeforeEach
    void setUp() {
        getUseCase = new GetUseCase(mapperUtils, questionRepository, answerRepository);
    }

    @Test
    public void getHappyPass() {
        Question question = new Question();
        QuestionDTO questionDTO = new QuestionDTO("12334", "asdf", "Cuanto es 2 + 2", "Matematicas", "Operaciones");
        question.setId(questionDTO.getId());
        question.setUserId(questionDTO.getUserId());
        question.setQuestion(questionDTO.getQuestion());
        question.setType(questionDTO.getType());
        question.setCategory(questionDTO.getCategory());

        List<AnswerDTO> answersDTO = new ArrayList<>();

        Answer answer = new Answer();
        AnswerDTO answerDTO = new AnswerDTO("asdf", "12334", "Es 4",1, LocalDateTime.of(2022, 06, 10, 8, 0));
        answer.setQuestionId(answerDTO.getQuestionId());
        answer.setUserId(answerDTO.getUserId());
        answer.setAnswer(answerDTO.getAnswer());
        answer.setCreateAt(answerDTO.getCreateAt());
        answer.setPosition(answerDTO.getPosition());

        answersDTO.add(answerDTO);

        questionDTO.setAnswers(answersDTO);

        Mockito.when(questionRepository.findById(questionDTO.getId())).thenReturn(Mono.just(question));
        Mockito.when(answerRepository.findAllByQuestionId(questionDTO.getId())).thenReturn(Flux.just(answer));

        Mono<QuestionDTO> publisher = getUseCase.apply(question.getId());

        StepVerifier
                .create(publisher)
                .expectNext(questionDTO)
                .verifyComplete();

        Mockito.verify(questionRepository).findById(question.getId());
    }

}