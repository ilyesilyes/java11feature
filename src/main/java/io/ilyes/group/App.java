package io.ilyes.group;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import jakarta.annotation.Nonnull;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException {

        // string new feature
        String multiLine = "Ilyes helps \n \n developpers \n to explore Java.";
        List<String> lines = multiLine.lines()
                .filter(line -> !line.isBlank())
                .map(String::strip)
                .collect(Collectors.toList());

        assertThat(lines).containsExactly("Baeldung helps", "developers", "explore Java.");

        // file reading
        String dirPath = "C:\\Users";
        String prefixFile = "demo";
        String sufixFile = ".txt";
        String content = "Sample txt";
        Path filePath = Files.writeString(Files.createTempFile(Paths.get(dirPath), prefixFile, sufixFile), "Sample txt");
        String fileContent =  Files.readString(filePath);
        assertThat(fileContent).isEqualTo(content);

        // to array function
        List<String> samplelist = Arrays.asList("Java", "kotlin");
        String[] sampleArray = samplelist.toArray(String[]::new);
        assertThat(sampleArray).containsExactly("Java", "kotlin");

        //not predication feature
        List<String> sampleList = Arrays.asList("Java", "\n \n", "Kotlin", " ");
        List<String> withoutBlanks = sampleList.stream()
                .filter(Predicate.not(String::isBlank))
                .collect(Collectors.toList());
        assertThat(withoutBlanks).containsExactly("Java", "Kotlin");

        // var keyword
        List<String> exList = Arrays.asList("Java", "Kotlin");
        String resultString = exList.stream()
                .map((@Nonnull var x) -> x.toUpperCase())
                .collect(Collectors.joining(", "));
        assertThat(resultString).isEqualTo("JAVA, KOTLIN");

        //http feature
        HttpResponse<String> httpResponse;
        try (HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(20))
                .build()) {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://localhost:" + "8080"))
                    .build();
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        }
        assertThat(httpResponse.body()).isEqualTo("Hello from the server!");


        //identifie nested class
        assertThat(App.class.isNestmateOf(NestedClass.class)).isTrue();
        assertThat(App.NestedClass.class.getNestHost()).isEqualTo(App.class);

    }
    public static class NestedClass {

    }
}
