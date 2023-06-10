package com.outfit.planner.system.product.service;

import com.outfit.planner.system.product.service.domain.ProductDomainService;
import com.outfit.planner.system.product.service.domain.ProductDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ProductDomainService productDomainService() {
        return new ProductDomainServiceImpl();
    }
}
