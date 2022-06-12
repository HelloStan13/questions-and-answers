package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class CreateUseCase implements SaveQuestion {
    private final QuestionRepository questionRepository;
    private final MapperUtils mapperUtils;

    public CreateUseCase(MapperUtils mapperUtils, QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<String> apply(QuestionDTO newQuestion) {
        return questionRepository
                .save(mapperUtils.mapperToQuestion(null).apply(newQuestion))
                .map(Question::getId);
    }
    public Mono<String>editQuestion(QuestionDTO questionDTO){
        Objects.requireNonNull(questionDTO.getQuestion(),"Id requerido");
        return  questionRepository
                .save(mapperUtils.mapperToQuestion(questionDTO.getId()).apply(questionDTO))
                .map(Question::getId);
    }

}
