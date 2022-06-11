package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class UpdateUseCaseTest {

    @SpyBean
    UpdateUseCase updateUseCase;

    @Autowired
    private MapperUtils mapperUtils;

    @Mock
    private QuestionRepository questionRepository;

    @Test
    void updateHappyPass(){
        Question question = new Question();
        QuestionDTO questionDTO = new QuestionDTO("12334", "asdf", "Cuanto es 2 + 2", "Matematicas", "Operaciones");
        question.setId(questionDTO.getId());
        question.setUserId(questionDTO.getUserId());
        question.setQuestion(questionDTO.getQuestion());
        question.setType(questionDTO.getType());
        question.setCategory(questionDTO.getCategory());

        Mockito.when(questionRepository.save(any())).thenReturn(Mono.just(mapperUtils.mapperToQuestion(questionDTO.getId()).apply(questionDTO)));

        var result= updateUseCase.apply(questionDTO);

        StepVerifier
                .create(result)
                .expectNext(questionDTO.getId())
                .verifyComplete();

    }

}