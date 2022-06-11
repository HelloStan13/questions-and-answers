package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class CreateUseCaseTest {

    @SpyBean
    private CreateUseCase createUseCase;

    @Autowired
    private MapperUtils mapperUtils;

    @MockBean
    QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        createUseCase = new CreateUseCase(mapperUtils, questionRepository);
    }

    @Test
    void createTest() {
        Question question = new Question();
        QuestionDTO questionDTO = new QuestionDTO("12334", "asdf", "Cuanto es 2 + 2", "Matematicas", "Operaciones");
        question.setId(questionDTO.getId());
        question.setUserId(questionDTO.getUserId());
        question.setQuestion(questionDTO.getQuestion());
        question.setType(questionDTO.getType());
        question.setCategory(questionDTO.getCategory());

        List<AnswerDTO> answers = new ArrayList<>();
        questionDTO.setAnswers(answers);

        Mockito.when(questionRepository.save(any())).thenReturn(Mono.just(mapperUtils.mapperToQuestion(questionDTO.getId()).apply(questionDTO)));

        var result= createUseCase.apply(questionDTO);

        StepVerifier
                .create(result)
                .expectNext(question.getId())
                .verifyComplete();

    }

}