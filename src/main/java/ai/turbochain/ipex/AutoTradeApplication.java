package ai.turbochain.ipex;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//mapper包扫描
@MapperScan(basePackages={"ai.turbochain.ipex.mapper"})
//开启定时
@EnableScheduling
public class AutoTradeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoTradeApplication.class, args);
	}

}
