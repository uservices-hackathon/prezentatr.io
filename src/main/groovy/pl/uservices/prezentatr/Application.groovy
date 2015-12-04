package pl.uservices.prezentatr
import org.springframework.boot.SpringApplication
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.feign.EnableFeignClients
import org.springframework.cloud.sleuth.Sampler
import org.springframework.cloud.sleuth.sampler.AlwaysSampler
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryHealthIndicator
import org.springframework.cloud.zookeeper.discovery.ZookeeperServiceDiscovery
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@EnableDiscoveryClient
@EnableFeignClients
@Configuration
class Application {

    @Bean Sampler sampler() {
        return new AlwaysSampler();
    }

    @Bean ZookeeperDiscoveryHealthIndicator zookeeperDiscoveryHealthIndicator(
            ZookeeperServiceDiscovery zookeeperServiceDiscovery) {
        return new ZookeeperDiscoveryHealthIndicator(zookeeperServiceDiscovery, null) {
            @Override
            protected void doHealthCheck(Health.Builder builder) throws Exception {
                builder.up()
            }
        }
    }

    static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application)
        application.run(args)
    }
}
