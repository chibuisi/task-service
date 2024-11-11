package com.chibuisi.api.handler;

import com.chibuisi.api.exception.TaskNotFoundException;
import com.chibuisi.api.model.ErrorMessage;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
@Requires(classes = { TaskNotFoundException.class, ExceptionHandler.class })
public class TaskNotFoundExceptionHandler implements ExceptionHandler<TaskNotFoundException, HttpResponse<ErrorMessage>> {

    @Override
    public HttpResponse<ErrorMessage> handle(HttpRequest request, TaskNotFoundException exception) {

        ErrorMessage message = ErrorMessage.builder()
                .message(exception.getMessage()).status(false).build();
        return HttpResponse.notFound(message).
                status(HttpStatus.NOT_FOUND);
    }


}
