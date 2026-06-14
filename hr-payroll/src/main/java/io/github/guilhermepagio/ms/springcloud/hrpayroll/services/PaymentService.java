package io.github.guilhermepagio.ms.springcloud.hrpayroll.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.github.guilhermepagio.ms.springcloud.hrpayroll.entities.Payment;
import io.github.guilhermepagio.ms.springcloud.hrpayroll.entities.Worker;
import io.github.guilhermepagio.ms.springcloud.hrpayroll.feignclients.WorkerFeignClient;

@Service
public class PaymentService {

    private final WorkerFeignClient workerFeignClient;

    PaymentService(WorkerFeignClient workerFeignClient) {
        this.workerFeignClient = workerFeignClient;
    }

    public Payment getPayment(Long workerId, Integer days) {
        final Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("id", workerId.toString());

        final Worker worker = workerFeignClient.findById(workerId).getBody();

        if (worker == null) {
            throw new RuntimeException("Worker not found");
        }

        return new Payment(worker.getName(), worker.getDailyIncome(), days);
    }

}
