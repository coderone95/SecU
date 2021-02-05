package com.coderone95.secu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.io.File;

@SpringBootApplication
public class SecUApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecUApplication.class, args);
	}

}
