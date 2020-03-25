package com.shipping.analyser.service.impl;

import com.shipping.analyser.dto.SellerDto;
import com.shipping.analyser.model.SellerEntity;
import com.shipping.analyser.repository.SellerRepository;
import com.shipping.analyser.service.SellerEnrollmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class SellerEnrollmentServiceImpl  implements SellerEnrollmentService {

    private static final Logger logger = LoggerFactory.getLogger(SellerEnrollmentServiceImpl.class);

    @Autowired
    SellerRepository sellerRepository;

    @Override
    public Flux<SellerDto> findAllSellersEnrolled() {
        Flux<SellerEntity> fl= sellerRepository.findAll();
        return fl.map(x -> new SellerDto(x.getUsername(), x.getEnrolledDate(),x.getModifiedDate()));
    }

    @Override
    public Mono<Boolean> isSellerEnrolled(String username) {
       // Mono<SellerEntity> mono= sellerRepository.findByUserName(username) ;
        return sellerRepository.findByUserName(username).hasElement().log();
    }

    @Override
    public Mono<SellerDto> enrollSeller(String username) {
        SellerEntity seller = new SellerEntity();
        seller.setUsername(username);
        LocalDateTime ll = LocalDateTime.now(ZoneId.of(ZoneId.SHORT_IDS.get("PST")));
        seller.setEnrolledDate(ll);
        seller.setModifiedDate(ll);
        return sellerRepository.save(seller).map(x-> new SellerDto(x.getUsername(),x.getEnrolledDate(),x.getModifiedDate()));
    }

    @Override
    public Mono<ResponseEntity<Void>> disenrollSeller(String username){
        logger.debug("Started disenrollSeller");
        return sellerRepository.findByUserName(username)
                .flatMap(existingSeller ->  sellerRepository.delete(existingSeller)
                            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
