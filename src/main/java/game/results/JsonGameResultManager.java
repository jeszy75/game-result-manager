
package game.results;

import lombok.NonNull;
import util.JacksonHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonGameResultManager implements GameResultManager {

    private File file;

    public JsonGameResultManager(@NonNull File file) {
        this.file = file;
    }

    @Override
    public List<GameResult> add(@NonNull GameResult result) throws IOException {
        var results = file.exists() ? getAll() : new ArrayList<GameResult>();
        results.add(result);
        try (var out = new FileOutputStream(file)) {
            JacksonHelper.writeList(out, results);
        }
        return results;
    }

    public List<GameResult> getAll() throws IOException {
        try (var in = new FileInputStream(file)) {
            return JacksonHelper.readList(in, GameResult.class);
        }
    }

}
