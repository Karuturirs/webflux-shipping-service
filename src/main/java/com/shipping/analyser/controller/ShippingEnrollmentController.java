package com.shipping.analyser.controller;


import com.shipping.analyser.dto.SellerDto;
import com.shipping.analyser.service.SellerEnrollmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.print.attribute.standard.Media;

@RestController
@RequestMapping(path = "/api/v1/shipping/enroll")
public class ShippingEnrollmentController {

    private static final Logger logger = LoggerFactory.getLogger(ShippingEnrollmentController.class);

    @Autowired
    SellerEnrollmentService sellerEnrollmentService;

    @Value("${admin.authenticate.token}")
    private String token;


    /**
     * Fetches all the sellers who are enrolled to new shipping program
     * @return
     */
    @GetMapping(path="/sellers", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<SellerDto> findAllSellersEnrolled(){
        return sellerEnrollmentService.findAllSellersEnrolled();
    }

    /**
     * Check if the seller is enrolled or not to the new shipping program.
     * @param username
     * @return
     */
    @GetMapping(path = "/sellers/{username}")
    public Mono<ResponseEntity<Boolean>> findEnrollemntByName(@PathVariable("username") String username) {
        return sellerEnrollmentService.isSellerEnrolled(username)
                .map(x-> ResponseEntity.ok(x))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Adds the seller to the new shipping program
     * @param authToken
     * @param sellersusername
     * @return
     */
    @PostMapping(path= "/seller" )
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<?> enrollSellerForShipping(@RequestHeader(name = "X-Admin-Authentication", required = true) String authToken,
                                           @RequestHeader(name = "sellers", required =true) String  sellersusername ){

        //Validate Token
        if(authToken.equals(token)){
            return sellerEnrollmentService.enrollSeller(sellersusername);
        }else{
            return Mono.just(new ResponseEntity(HttpStatus.UNAUTHORIZED));
        }
    }


    /**
     * Dis-enrolls the seller to the new shipping program.
     * @param username
     * @return
     */
    @DeleteMapping(path= "/seller/{username}" )
    public Mono<ResponseEntity<Void>> disenrollSeller(@PathVariable(value = "username") String username) {
        return sellerEnrollmentService.disenrollSeller(username);
    }




}
