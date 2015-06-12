package pl.devoxx.prezentatr.present

import com.wordnik.swagger.annotations.Api
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping('/feed')
@TypeChecked
@Api(value = "feed", description = "Collects changes in the brewing states")
class FeedController {
}
