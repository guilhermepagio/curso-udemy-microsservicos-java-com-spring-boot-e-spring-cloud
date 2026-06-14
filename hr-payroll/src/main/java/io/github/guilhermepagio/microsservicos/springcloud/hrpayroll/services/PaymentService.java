package io.github.guilhermepagio.microsservicos.springcloud.hrpayroll.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.guilhermepagio.microsservicos.springcloud.hrpayroll.entities.Payment;
import io.github.guilhermepagio.microsservicos.springcloud.hrpayroll.entities.Worker;

@Service
public class PaymentService {

    @Value("${hr-worker.host}")
    private String workerHost;

    private final RestTemplate restTemplate;

    PaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Payment getPayment(Long workerId, Integer days) {
        final Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("id", workerId.toString());

        final Worker worker = restTemplate.getForObject("http://" + workerHost + "/workers/{id}",
                Worker.class,
                uriVariables);

        return new Payment(worker.getName(), worker.getDailyIncome(), days);
    }

}
