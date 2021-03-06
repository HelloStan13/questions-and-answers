package co.com.sofka.questions.routers;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.usecases.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import java.util.function.Function;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class QuestionRouter {
    @Bean
    @RouterOperation(operation = @Operation(operationId = "getAllQuestions", summary = "Find all Questions", tags = {"Questions"},
            responses = {@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = QuestionDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Request"),
                    @ApiResponse(responseCode = "404", description = "Questions not found")}))
    public RouterFunction<ServerResponse> getAll(ListUseCase listUseCase) {
        return route(GET("/getAll"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(listUseCase.get(), QuestionDTO.class))
        );
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "getOwnerAllQuestions", summary = "Find questions by user", tags = {"Questions"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "userId")},
            responses = {@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = QuestionDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Request"),
                    @ApiResponse(responseCode = "404", description = "Questions not found")}))
    public RouterFunction<ServerResponse> getOwnerAll(OwnerListUseCase ownerListUseCase) {
        return route(
                GET("/getOwnerAll/{userId}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(
                                ownerListUseCase.apply(request.pathVariable("userId")),
                                QuestionDTO.class
                        ))
        );
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "createQuestion", summary = "Create a question", tags = {"Questions"},
            requestBody = @RequestBody(required = true, description = "Enter Request body as Json Object",
                    content = @Content(schema = @Schema(implementation = QuestionDTO.class))),
            responses = {@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = QuestionDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Request"),
                    @ApiResponse(responseCode = "404", description = "Questions not found")}))
    public RouterFunction<ServerResponse> create(CreateUseCase createUseCase) {
        Function<QuestionDTO, Mono<ServerResponse>> executor = questionDTO -> createUseCase.apply(questionDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(result));

        return route(
                POST("/create").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(QuestionDTO.class).flatMap(executor)
        );
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "getAQuestion", summary = "Find a question", tags = {"Questions"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id")},
            responses = {@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = QuestionDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Request"),
                    @ApiResponse(responseCode = "404", description = "Question not found")}))
    public RouterFunction<ServerResponse> get(GetUseCase getUseCase) {
        return route(
                GET("/get/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getUseCase.apply(
                                        request.pathVariable("id")),
                                QuestionDTO.class
                        ))
        );
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "addAnswer", summary = "Add an answer", tags = {"Answers"},
            requestBody = @RequestBody(required = true, description = "Enter Request body as Json Object",
                    content = @Content(schema = @Schema(implementation = AnswerDTO.class))),
            responses = {@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = AnswerDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Request"),
                    @ApiResponse(responseCode = "404", description = "Answer not found")}))
    public RouterFunction<ServerResponse> addAnswer(AddAnswerUseCase addAnswerUseCase) {
        return route(POST("/add").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(AnswerDTO.class)
                        .flatMap(addAnswerDTO -> addAnswerUseCase.apply(addAnswerDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                        )
        );
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "delete", summary = "Delete a question", tags = {"Questions"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id")},
            responses = {@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = QuestionDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Request"),
                    @ApiResponse(responseCode = "404", description = "Question not found")}))
    public RouterFunction<ServerResponse> delete(DeleteUseCase deleteUseCase) {
        return route(
                DELETE("/delete/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteUseCase.apply(request.pathVariable("id")), Void.class))
        );
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "editQuestion", summary = "Edit a question", tags = {"Questions"},
            requestBody = @RequestBody(required = false, description = "Enter Request body as Json Object",
                    content = @Content(schema = @Schema(implementation = QuestionDTO.class))),
            responses = {@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = QuestionDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Request"),
                    @ApiResponse(responseCode = "404", description = "Questions not found")}))
    public RouterFunction<ServerResponse> update(CreateUseCase createUseCase) {
        Function<QuestionDTO, Mono<ServerResponse>> executor = questionDTO -> createUseCase.editQuestion(questionDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(result));

        return route(
                PUT("/updateQ").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(QuestionDTO.class).flatMap(executor)
        );
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "updateAnswer", summary = "Update  answer", tags = {"Answers"},
            requestBody = @RequestBody(required = true, description = "Enter Request body as Json Object",
                    content = @Content(schema = @Schema(implementation = AnswerDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation question Id", content = @Content(schema = @Schema(implementation = AnswerDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Request")}))
    public RouterFunction<ServerResponse> updateAnswer(AddAnswerUseCase addAnswerUseCase) {
        Function<AnswerDTO, Mono<ServerResponse>> executor = AnswerDTO -> addAnswerUseCase.editAnswer(AnswerDTO).flatMap(r -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).bodyValue(r));
        return route(PUT("/editAnswer").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(AnswerDTO.class).flatMap(executor));
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "getTotalPages", summary = "Find number of question pages",
            responses = @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Integer.class)))))
    public RouterFunction<ServerResponse> getTotalPages(ListUseCase listUseCase) {
        return route(GET("/getTotalPages"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(listUseCase.getTotalPages(), Integer.class)));
    }
    @Bean
    @RouterOperation(operation = @Operation(operationId = "getQuestionPaged", summary = "Find all questions pageable", tags = { "Pageable question" },
            parameters = { @Parameter(in = ParameterIn.PATH, name = "page") },
            responses = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = QuestionDTO.class)))),
                    @ApiResponse(responseCode = "400", description = "Invalid Request"),
                    @ApiResponse(responseCode = "404", description = "Question not found") }))
    public RouterFunction<ServerResponse> getQuestionPageable(ListUseCase listUseCase) {
        return route(GET("/pagination/{page}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(
                                listUseCase.getPage(Integer.parseInt(request.pathVariable("page"))),
                                QuestionDTO.class
                        )));
    }


}