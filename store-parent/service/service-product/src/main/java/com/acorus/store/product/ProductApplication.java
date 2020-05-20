package com.acorus.store.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author CheneAntray
 * @create 2020-05-12 20:07
 */
@SpringBootApplication
@ComponentScan({"com.acorus.store"})
@EnableDiscoveryClient
//@EnableSwagger2
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class,args);
    }
}
