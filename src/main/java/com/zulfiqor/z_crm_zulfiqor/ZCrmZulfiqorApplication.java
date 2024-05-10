package com.zulfiqor.z_crm_zulfiqor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZCrmZulfiqorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZCrmZulfiqorApplication.class, args);
		System.out.println("Started");
	}

	// todo-list
	// add scheduler that checks each transaction make it sold_out when products finished
	// code optimization
	// code optimization in inProducts and other jpa services
	// get in-product by id to check the quantity
	// merge unblocked | blocked apis
	// from the second release change the statistics, move to JPA!
}
