package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class UpdateAnswerUseCase implements SaveAnswer {
    private final MapperUtils mapper;
    private final AnswerRepository answerRepository;
    private final GetUseCase getUseCase;

    public UpdateAnswerUseCase(MapperUtils mapper, AnswerRepository answerRepository, GetUseCase getUseCase) {
        this.mapper = mapper;
        this.answerRepository = answerRepository;
        this.getUseCase = getUseCase;
    }

    @Override
    public Mono<QuestionDTO> apply(AnswerDTO answerDTO) {
        Objects.requireNonNull(answerDTO.getQuestionId(), "Id of the question is required");
        return getUseCase.apply(answerDTO.getQuestionId()).flatMap(question ->
                answerRepository.save(mapper.mapperToAnswer().apply(answerDTO))
                .map(Answer::getQuestionId)
                        .map(answer -> {
                            question.getAnswers().add(answerDTO);
                            return question;
                        }));
    }
}