package com.estock.market;

import com.estock.market.config.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({AxonConfig.class})
public class EstockmarketUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstockmarketUserServiceApplication.class, args);
    }

}
