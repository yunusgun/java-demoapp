package demo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import javax.annotation.PostConstruct;
import io.pyroscope.javaagent.PyroscopeAgent;
import io.pyroscope.javaagent.config.Config;
import io.pyroscope.javaagent.EventType;
import io.pyroscope.http.Format;

@SpringBootApplication
public class JavaDemoapp {

    public static void main(String[] args) {
        SpringApplication.run(JavaDemoapp.class, args);
    }

    @PostConstruct
    public void init() {
        PyroscopeAgent.start(
            new Config.Builder()
                .setApplicationName("yunus-java-demo-app")
                .setProfilingEvent(EventType.ITIMER)
                .setFormat(Format.JFR)
                .setServerAddress("http://35.221.186.93:30002")
                .build()
        );
    }
}
