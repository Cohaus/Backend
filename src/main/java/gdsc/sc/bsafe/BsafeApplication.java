package gdsc.sc.bsafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BsafeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BsafeApplication.class, args);
    }

}
