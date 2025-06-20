import parser.ParserJson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        ParserJson parser = new ParserJson();

        String fileName = "json.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String str = reader.lines().collect(Collectors.joining());

            System.out.println(str);

            parser.parse(str);
        } catch (IOException e) {
            e.getMessage();
        }
    }
}