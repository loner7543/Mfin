package tools;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FileUtils {
    public static void writeFile(String path ,List<String> data) throws IOException {
        Path file = Paths.get(path);
        Files.write(file,data, Charset.forName("UTF-8"));
    }
}
