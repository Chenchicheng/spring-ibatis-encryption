package encryption;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenchicheng
 * @date 2024/3/27
 */
@SpringBootApplication
@MapperScan({"encryption.mapper"})
public class EncryptApplication {

    public static void main(String[] args) {
        SpringApplication.run(EncryptApplication.class, args);
    }
}
