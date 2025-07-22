package pl.bdygasinski.gameoflife.rest;

import org.junit.jupiter.params.provider.Arguments;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TestUtils {

    public static Stream<Arguments> boardValidCases() throws IOException, URISyntaxException {
        String dirRoot = "/json/boards/valid";
        return getFileContentsStream(dirRoot, getValidFilesContents());
    }

    public static Stream<Arguments> boardInvalidCases() throws IOException, URISyntaxException {
        String dirRoot = "/json/boards/invalid";
        return getFileContentsStream(dirRoot, getInvalidFilesContents());
    }

    static Stream<Arguments> getFileContentsStream(String dirRoot, Function<Path, Arguments> getFileContents) throws IOException, URISyntaxException {
        var resourceUrl = TestUtils.class.getResource(dirRoot);

        if (resourceUrl == null) {
            throw new IllegalStateException("Test cases folder not found: %s".formatted(dirRoot));
        }

        Path boardsDir = Path.of(resourceUrl.toURI());

        try (var paths = Files.list(boardsDir)) {
            return paths
                    .filter(Files::isDirectory)
                    .filter(isDirectoryDigit())
                    .sorted()
                    .map(getFileContents)
                    .toList()
                    .stream();
        }
    }

    static Function<Path, Arguments> getValidFilesContents() {
        return dir -> {
            try {
                String inputJson = Files.readString(dir.resolve("input.json"));
                String expectedJson = Files.readString(dir.resolve("expected.json"));
                return Arguments.of(inputJson, expectedJson);
            } catch (IOException e) {
                throw new UncheckedIOException("Failed to read JSON files in " + dir, e);
            }
        };
    }

    static Function<Path, Arguments> getInvalidFilesContents() {
        return dir -> {
            try {
                String inputJson = Files.readString(dir.resolve("input.json"));
                return Arguments.of(inputJson);
            } catch (IOException e) {
                throw new UncheckedIOException("Failed to read JSON files in " + dir, e);
            }
        };
    }

    static Predicate<Path> isDirectoryDigit() {
        return path -> path.getFileName().toString().matches("\\d+");
    }
}