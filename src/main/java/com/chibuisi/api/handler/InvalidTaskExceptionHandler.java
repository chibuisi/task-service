package com.chibuisi.api.handler;

import com.chibuisi.api.exception.InvalidTaskException;
import com.chibuisi.api.exception.TaskNotFoundException;
import com.chibuisi.api.model.ErrorMessage;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
@Requires(classes = { InvalidTaskException.class, ExceptionHandler.class })
public class InvalidTaskExceptionHandler implements ExceptionHandler<InvalidTaskException, HttpResponse<ErrorMessage>> {

    @Override
    public HttpResponse<ErrorMessage> handle(HttpRequest request, InvalidTaskException exception) {

        ErrorMessage message = ErrorMessage.builder()
                .message(exception.getMessage()).status(false).build();
        return HttpResponse.serverError(message).
                status(HttpStatus.NOT_FOUND);
    }

}
