import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.*;

public class FileTest {
    @Test
    @SneakyThrows
    public void Test() {
        try (FileOutputStream outputStream = new FileOutputStream("output.txt");
             FileInputStream inputStream = new FileInputStream("output.txt")) {
            outputStream.write("zzp".getBytes());
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader reader1 = new BufferedReader(reader);
            System.out.println(reader1.readLine());
        }
    }
}
