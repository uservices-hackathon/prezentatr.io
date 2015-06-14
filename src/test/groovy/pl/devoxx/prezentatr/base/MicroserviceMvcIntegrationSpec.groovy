package pl.devoxx.prezentatr.base

import com.ofg.infrastructure.base.MvcIntegrationSpec
import pl.devoxx.prezentatr.Application
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [Application], loader = SpringApplicationContextLoader)
class MicroserviceMvcIntegrationSpec extends MvcIntegrationSpec {
}
