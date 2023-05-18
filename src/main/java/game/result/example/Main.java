package game.result.example;

import com.github.javafaker.Faker;
import game.result.GameResult;
import game.result.GameResultManager;
import game.result.JsonGameResultManager;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Locale;

public class Main {

    private static final Faker faker = new Faker(Locale.ENGLISH);

    private static GameResult createGameResult() {
        return GameResult.builder()
                .playerName(faker.name().firstName())
                .solved(faker.bool().bool())
                .steps(faker.number().numberBetween(10, 50))
                .duration(Duration.ofSeconds(faker.number().numberBetween(10, 100)))
                .created(ZonedDateTime.now().minusMinutes(faker.number().numberBetween(0, 60)))
                .build();
    }

    public static void main(String[] args) throws IOException {
        GameResultManager manager = new JsonGameResultManager(Path.of("results.json"));
        for (var i = 0; i < 100; i++) {
            manager.add(createGameResult());
        }
        manager.getBest(10).forEach(System.out::println);
    }

}
