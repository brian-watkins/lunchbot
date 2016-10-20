package io.pivotal.boston;

import me.ramswaroop.jbot.core.slack.SlackDao;
import me.ramswaroop.jbot.core.slack.SlackService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LunchbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(LunchbotApplication.class, args);
	}


	@Bean
	public SlackService slackService () {
		return new SlackService();
	}

	@Bean
	public SlackDao slackDao () {
		return new SlackDao();
	}
}
