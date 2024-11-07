package com.chibuisi.infrastructure.postgres.enitity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public enum Status {
    PENDING, IN_PROGRESS, COMPLETED;
}
