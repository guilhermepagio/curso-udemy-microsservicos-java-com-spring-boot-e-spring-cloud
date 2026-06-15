package io.github.guilhermepagio.ms.springcloud.hrpayroll.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.github.guilhermepagio.ms.springcloud.hrpayroll.entities.Payment;
import io.github.guilhermepagio.ms.springcloud.hrpayroll.services.PaymentService;

@RestController
@RequestMapping(value = "/payments")
public class PaymentResource {

    private final PaymentService paymentService;

    PaymentResource(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @HystrixCommand(fallbackMethod = "getPaymentFallback")
    @GetMapping("/{idWorker}/days/{qtdDays}")
    public ResponseEntity<Payment> getMethodName(@PathVariable Long idWorker, @PathVariable Integer qtdDays) {
        final Payment payment = paymentService.getPayment(idWorker, qtdDays);
        return ResponseEntity.ok(payment);
    }

    public ResponseEntity<Payment> getPaymentFallback(Long idWorker, Integer qtdDays) {
        final Payment payment = new Payment("Brann", 400.0, qtdDays);
        return ResponseEntity.ok(payment);
    }

}
