package io.github.guilhermepagio.ms.springcloud.hrpayroll.services;

import io.github.guilhermepagio.ms.springcloud.hrpayroll.entities.Payment;
import io.github.guilhermepagio.ms.springcloud.hrpayroll.entities.Worker;
import io.github.guilhermepagio.ms.springcloud.hrpayroll.feignclients.WorkerFeignClient;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final WorkerFeignClient workerFeignClient;

    PaymentService(WorkerFeignClient workerFeignClient) {
        this.workerFeignClient = workerFeignClient;
    }

    public Payment getPayment(Long workerId, Integer days) {
        final Worker worker = workerFeignClient.findById(workerId).getBody();

        if (worker == null) {
            throw new RuntimeException("Worker not found");
        }

        return new Payment(worker.getName(), worker.getDailyIncome(), days);
    }

}
