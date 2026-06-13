package io.github.guilhermepagio.microsservicos.springcloud.hrworker.resources;

import io.github.guilhermepagio.microsservicos.springcloud.hrworker.entities.Worker;
import io.github.guilhermepagio.microsservicos.springcloud.hrworker.repositories.WorkerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/workers")
public class WorkerResource {

    private final WorkerRepository workerRepository;

    WorkerResource(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @GetMapping
    public ResponseEntity<List<Worker>> findAll() {
        final List<Worker> list = workerRepository.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Worker> findById(@PathVariable @NonNull Long id) {
        final Worker worker = workerRepository.findById(id).get();
        return ResponseEntity.ok(worker);
    }
}
