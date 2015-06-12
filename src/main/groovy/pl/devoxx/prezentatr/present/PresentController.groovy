package pl.devoxx.prezentatr.present

import com.wordnik.swagger.annotations.Api
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping('/present')
@TypeChecked
@Api(value = "pairId", description = "Collects places from tweets and propagates them to Collerators")
class PresentController {
}
