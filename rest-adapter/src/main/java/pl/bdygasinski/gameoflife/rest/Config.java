package pl.bdygasinski.gameoflife.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.bdygasinski.gameoflife.domain.GameStateFactory;

@Configuration
class Config {

    @Bean
    public GameStateFactory gameStateFactory() {
        return new GameStateFactory();
    }
}