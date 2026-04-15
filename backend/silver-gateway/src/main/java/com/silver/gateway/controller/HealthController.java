package com.silver.gateway.controller;

import com.silver.common.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/gateway")
public class HealthController {

    @GetMapping("/ping")
    public Mono<R<String>> ping() {
        return Mono.just(R.success("gateway alive"));
    }
}
