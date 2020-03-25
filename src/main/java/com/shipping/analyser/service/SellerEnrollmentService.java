package com.shipping.analyser.service;

import com.shipping.analyser.dto.SellerDto;
import com.shipping.analyser.model.SellerEntity;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SellerEnrollmentService {

    public Flux<SellerDto> findAllSellersEnrolled();

    public Mono<Boolean> isSellerEnrolled(String username);

    public Mono<SellerDto> enrollSeller(String username);

    public Mono<ResponseEntity<Void>> disenrollSeller(String username);
}
