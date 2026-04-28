package com.silver.gateway.controller;

import com.silver.common.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 网关健康检查控制器。
 * 提供网关服务可用性检测接口。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@RestController
@RequestMapping("/gateway")
public class HealthController {

    /**
     * 网关健康检查。
     *
     * @return 网关存活状态
     */
    @GetMapping("/ping")
    public Mono<R<String>> ping() {
        return Mono.just(R.success("gateway alive"));
    }
}
