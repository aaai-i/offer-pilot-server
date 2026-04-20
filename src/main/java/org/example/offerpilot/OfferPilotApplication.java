package org.example.offerpilot;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.offerpilot.mapper")
public class OfferPilotApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfferPilotApplication.class, args);
    }

}
