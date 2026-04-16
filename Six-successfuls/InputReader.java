import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputReader {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String readLine(String prompt) throws IOException {
        System.out.print(prompt);
        return reader.readLine();
    }
}
