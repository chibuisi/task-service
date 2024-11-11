package com.chibuisi.api.model;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Serdeable
public class ErrorMessage {
    private String message;
    private Boolean status;
}
