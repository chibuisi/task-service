package com.chibuisi.api.model;

import com.chibuisi.domain.model.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDto {
    @JsonProperty
    private Long id;
    @JsonProperty
    @NotBlank
    private String title;
    @JsonProperty
    @Size(message = "Description length is too long", max = 1500) private String description;
    @JsonProperty
    private Instant createdTime;
    @JsonProperty
    private Instant updatedTime;
    @JsonProperty
    @NotNull
    private TaskStatus status;
}
