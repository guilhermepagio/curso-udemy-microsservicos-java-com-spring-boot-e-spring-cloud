package io.github.guilhermepagio.microsservicos.springcloud.hrpayroll.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.guilhermepagio.microsservicos.springcloud.hrpayroll.entities.Payment;
import io.github.guilhermepagio.microsservicos.springcloud.hrpayroll.services.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value = "/payments")
public class PaymentResource {

    private final PaymentService paymentService;

    PaymentResource(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{idWorker}/days/{qtdDays}")
    public ResponseEntity<Payment> getMethodName(@PathVariable Long idWorker, @PathVariable Integer qtdDays) {
        final Payment payment = paymentService.getPayment(idWorker, qtdDays);
        return ResponseEntity.ok(payment);
    }

}
