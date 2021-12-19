package com.devnity.devnity;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DevnityApplication {

  public static void main(String[] args) {

    SpringApplication.run(DevnityApplication.class, args);
  }

  // App-context 실행시 Timezone 변경
  @PostConstruct
  public void started() {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
  }
}

