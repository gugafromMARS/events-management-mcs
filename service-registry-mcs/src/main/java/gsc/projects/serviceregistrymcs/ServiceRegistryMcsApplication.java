package gsc.projects.serviceregistrymcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistryMcsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRegistryMcsApplication.class, args);
	}

}
