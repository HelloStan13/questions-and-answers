package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class UpdateAnswerUseCase {
    private final MapperUtils mapperUtils;
    private final AnswerRepository answerRepository;
    private final GetUseCase getUseCase;

    public UpdateAnswerUseCase(MapperUtils mapperUtils, AnswerRepository answerRepository, GetUseCase getUseCase) {
        this.mapperUtils = mapperUtils;
        this.answerRepository = answerRepository;
        this.getUseCase = getUseCase;
    }

    public Mono<String>editAnswer(AnswerDTO answerDTO){
        Objects.requireNonNull(answerDTO.getQuestionId(),"Id requerido");
        return  answerRepository
                .save(mapperUtils.mapperToAnswer().apply(answerDTO))
                .map(Answer::getId);
    }

}