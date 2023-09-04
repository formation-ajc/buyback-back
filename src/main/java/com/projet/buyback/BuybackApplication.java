package com.projet.buyback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.net.URI;

@SpringBootApplication
public class BuybackApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuybackApplication.class, args);

		openSwagger();
	}

	private static void openSwagger() {
		System.setProperty("java.awt.headless", "false");
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.browse(new URI("http://localhost:8080/swagger-ui/index.html"));
		}
		catch(Exception e) {
			System.out.println("ERROR LOADING PAGE");
		}
	}

}
