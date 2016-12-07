package urlShort;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import urlShort.utils.DBInitializator;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        DBInitializator.getDbInitializator();
        SpringApplication.run(Application.class, args);
    }
}
