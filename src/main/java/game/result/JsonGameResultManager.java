package game.result;

import lombok.NonNull;
import util.JacksonHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JsonGameResultManager implements GameResultManager {

    private Path filePath;

    public JsonGameResultManager(@NonNull Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<GameResult> add(@NonNull GameResult result) throws IOException {
        var results = Files.exists(filePath) ? getAll() : new ArrayList<GameResult>();
        results.add(result);
        try (var out = Files.newOutputStream(filePath)) {
            JacksonHelper.writeList(out, results);
        }
        return results;
    }

    public List<GameResult> getAll() throws IOException {
        try (var in = Files.newInputStream(filePath)) {
            return JacksonHelper.readList(in, GameResult.class);
        }
    }

}
