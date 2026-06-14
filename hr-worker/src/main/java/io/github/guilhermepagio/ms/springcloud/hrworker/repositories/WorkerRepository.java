package io.github.guilhermepagio.ms.springcloud.hrworker.repositories;

import io.github.guilhermepagio.ms.springcloud.hrworker.entities.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

}
