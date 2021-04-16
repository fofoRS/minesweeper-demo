package org.clone.minesweeper;

import org.clone.minesweeper.config.GameParameterProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(GameParameterProperties.class)
public class CloneMinesweeperApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloneMinesweeperApplication.class, args);
	}
}
