package ru.yastrebov.agreement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class AgreementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgreementApplication.class, args);
	}

}
