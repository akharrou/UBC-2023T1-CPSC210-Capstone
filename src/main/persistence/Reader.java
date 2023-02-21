package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

// Represents a reader that reads data stored in a file.
public class Reader {

    // EFFECTS: reads source file as string and returns it
    //          throws IOException if an I/O error occurs whilst opening the file
    public static String read(String sourcePath) throws IOException {
        return Files.lines(Paths.get(sourcePath), StandardCharsets.UTF_8).collect(Collectors.joining());
    }

}
