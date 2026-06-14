package io.github.guilhermepagio.microsservicos.springcloud.hrworker.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.guilhermepagio.microsservicos.springcloud.hrworker.entities.Worker;
import io.github.guilhermepagio.microsservicos.springcloud.hrworker.repositories.WorkerRepository;

@RestController
@RequestMapping(value = "/workers")
public class WorkerResource {

    private static Logger logger = LoggerFactory.getLogger(WorkerResource.class);

    private final Environment environment;

    private final WorkerRepository workerRepository;

    WorkerResource(WorkerRepository workerRepository, Environment environment) {
        this.workerRepository = workerRepository;
        this.environment = environment;
    }

    @GetMapping
    public ResponseEntity<List<Worker>> findAll() {
        final List<Worker> list = workerRepository.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Worker> findById(@PathVariable @NonNull Long id) {
        final Worker worker = workerRepository.findById(id).get();

        if (worker == null) {
            return ResponseEntity.notFound().build();
        }

        logger.info("PORT = " + environment.getProperty("local.server.port"));

        return ResponseEntity.ok(worker);
    }
}
