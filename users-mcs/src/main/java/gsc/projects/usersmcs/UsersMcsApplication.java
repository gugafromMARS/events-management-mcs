package gsc.projects.usersmcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UsersMcsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersMcsApplication.class, args);
	}

}
