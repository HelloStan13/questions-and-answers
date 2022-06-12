package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DeleteUseCaseTest {

    @SpyBean
    private DeleteUseCase deleteUseCase;

    @MockBean
    QuestionRepository questionRepository;

    @MockBean
    AnswerRepository answerRepository;

    @Test
    void deleteHappyPass(){
        Question question = new Question();
        QuestionDTO questionDTO = new QuestionDTO("12334", "asdf", "Cuanto es 2 + 2", "Matematicas", "Operaciones");
        question.setId(questionDTO.getId());
        question.setUserId(questionDTO.getUserId());
        question.setQuestion(questionDTO.getQuestion());
        question.setType(questionDTO.getType());
        question.setCategory(questionDTO.getCategory());

        Answer answer = new Answer();
        AnswerDTO answerDTO = new AnswerDTO("4654665fgf", "12aa334", "564b6fb", "que es java", 4, LocalDateTime.of(2022, 06, 10, 8, 0, 0));
        answer.setQuestionId(answerDTO.getQuestionId());
        answer.setUserId(answerDTO.getUserId());
        answer.setAnswer(answerDTO.getAnswer());
        answer.setCreateAt(answerDTO.getCreateAt());

        List<AnswerDTO> answersDTO = new ArrayList<>();

        answersDTO.add(answerDTO);

        questionDTO.setAnswers(answersDTO);

        Mockito.when(questionRepository.deleteById(question.getId())).thenReturn(Mono.empty());
        Mockito.when(answerRepository.deleteByQuestionId(question.getId())).thenReturn(Mono.empty());

        var result = deleteUseCase.apply(question.getId());

        StepVerifier
                .create(result)
                .expectNext()
                .verifyComplete();

    }

}