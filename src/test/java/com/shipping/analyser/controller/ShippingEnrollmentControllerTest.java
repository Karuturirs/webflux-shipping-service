package com.shipping.analyser.controller;

import com.shipping.analyser.dto.SellerDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/*@RunWith(SpringRunner.class)
@WebFluxTest*/
public class ShippingEnrollmentControllerTest {

    /*@Autowired
    WebTestClient webTestClient;

    @Test
    public void findAllSellersEnrolled_VerifyAPITypes(){

       webTestClient.get().uri("/api/v1/shipping/enroll/sellers")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON_VALUE)
                .expectBodyList(SellerDto.class);
                //.hasSize(1);
    }

    @Test
    public void findAllSellersEnrolled_VerfyApiData() {
        Flux<SellerDto> sellers = webTestClient.get().uri("/api/v1/shipping/enroll/sellers")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(SellerDto.class)
                .getResponseBody();

       StepVerifier.create(sellers)
                .expectSubscription()
               // .expectNext(SellerDto.class)
                .expectNextCount(1)
                .verifyComplete();
    }*/
}
