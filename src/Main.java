import parser.ParserJson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ParserJson parser = new ParserJson();

        String fileName = "json_key.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String str = reader.readLine();

            System.out.println(parser.readValue(str));
        } catch (IOException e) {
            e.getMessage();
        }
    }
}