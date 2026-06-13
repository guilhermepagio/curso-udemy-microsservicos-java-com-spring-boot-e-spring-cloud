package io.github.guilhermepagio.microsservicos.springcloud.hrworker.repositories;

import io.github.guilhermepagio.microsservicos.springcloud.hrworker.entities.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

}
