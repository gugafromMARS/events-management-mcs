package gsc.projects.tickehubmcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TickehubMcsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TickehubMcsApplication.class, args);
	}

}
