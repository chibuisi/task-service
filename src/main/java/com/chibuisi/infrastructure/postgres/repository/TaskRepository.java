package com.chibuisi.infrastructure.postgres.repository;

import com.chibuisi.infrastructure.postgres.enitity.TaskEntity;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface TaskRepository extends CrudRepository<TaskEntity, Long> {
}
