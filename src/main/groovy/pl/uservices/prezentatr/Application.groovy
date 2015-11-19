package pl.uservices.prezentatr
import com.ofg.infrastructure.environment.EnvironmentSetupVerifier
import groovy.transform.TypeChecked
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.scheduling.annotation.EnableAsync

import static com.ofg.config.BasicProfiles.DEVELOPMENT
import static com.ofg.config.BasicProfiles.PRODUCTION
import static com.ofg.config.BasicProfiles.TEST

@TypeChecked
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
class Application {

    static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application)
        application.addListeners(new EnvironmentSetupVerifier([DEVELOPMENT, PRODUCTION, TEST]))
        application.run(args)
    }
}
