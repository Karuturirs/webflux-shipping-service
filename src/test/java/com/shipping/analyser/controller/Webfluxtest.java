package com.shipping.analyser.controller;


import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


public class Webfluxtest {


    @Test
    public void testing_noelements(){
        Flux<Boolean> test = Flux.just();

       Mono<Boolean> dd = test.reduce( (x, y) -> (x & y)).log();

        StepVerifier.create(dd)
                .expectSubscription()
                .expectNextCount(0)
                //.expectNext(true)
                //.expectNextCount(1)
                .verifyComplete();

    }

    @Test
    public void testing_oneelements(){
        Flux<Boolean> test = Flux.just(false);

        Mono<Boolean> dd = test.reduce( (x, y) -> (x & y)).log();

        StepVerifier.create(dd)
                .expectSubscription()
                //.expectNextCount(1)
                .expectNext(false)
                //.expectNextCount(1)
                .verifyComplete();

    }

    @Test
    public void testing_oneTrueElements(){
        Flux<Boolean> test = Flux.just(true);

        Mono<Boolean> dd = test.reduce( (x, y) -> (x & y)).log();

        StepVerifier.create(dd)
                .expectSubscription()
                //.expectNextCount(1)
                .expectNext(true)
                //.expectNextCount(1)
                .verifyComplete();

    }

    @Test
    public void testing_TwoElements(){
        Flux<Boolean> test = Flux.just(true, true);

        Mono<Boolean> dd = test.reduce( (x, y) -> (x & y)).log();

        StepVerifier.create(dd)
                .expectSubscription()
                //.expectNextCount(1)
                .expectNext(false)
                .expectNextCount(0)
                .verifyComplete();

    }





}
