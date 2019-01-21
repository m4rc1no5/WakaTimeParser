package pl.marceen.wakatimeparser.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Marcin Zaremba
 */
class FileWriter {
    private static final Logger logger = LoggerFactory.getLogger(FileWriter.class);

    static void write(String content, String filename) throws IOException {
        logger.info("Writing content into file");

        Path path = Paths.get(filename);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(content);
        }
    }
}
