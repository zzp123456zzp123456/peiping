import edu.is.service.Service;
import org.junit.jupiter.api.Test;

public class MainTest {
    Service service;

    @Test
    public void method() {
        service = new Service();
        System.out.println(service.findUser("zzp", "123456"));
    }
}
