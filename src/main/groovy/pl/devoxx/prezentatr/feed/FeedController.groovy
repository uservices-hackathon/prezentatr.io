package pl.devoxx.prezentatr.feed

import com.wordnik.swagger.annotations.Api
import com.wordnik.swagger.annotations.ApiOperation
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.http.HttpEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.devoxx.prezentatr.config.Collaborators

import static org.springframework.web.bind.annotation.RequestMethod.PUT
import static pl.devoxx.prezentatr.config.Versions.AGREGATR_CONTENT_TYPE_V1
import static pl.devoxx.prezentatr.config.Versions.PREZENTATR_JSON_VERSION_1

@Slf4j
@RestController
@RequestMapping('/feed')
@TypeChecked
@Api(value = "feed", description = "Collects changes in the brewing states")
class FeedController {

    @RequestMapping(
            value = "/dojrzewatr/{id}",
            produces = PREZENTATR_JSON_VERSION_1,
            consumes = PREZENTATR_JSON_VERSION_1,
            method = PUT)
    @ApiOperation(value = "sends an order to agregatr")
    public String dojrzewatr(@PathVariable String id) {
        log.info("new dojrzewatr id: ${id}")
    }

    @RequestMapping(
            value = "/butelkatr/{id}",
            produces = PREZENTATR_JSON_VERSION_1,
            consumes = PREZENTATR_JSON_VERSION_1,
            method = PUT)
    @ApiOperation(value = "sends an order to butelkatr")
    public String butelkatr(@PathVariable String id) {
        log.info("new butelkatr id: ${id}")
    }
}
