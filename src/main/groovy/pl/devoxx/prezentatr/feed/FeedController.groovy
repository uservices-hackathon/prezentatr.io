package pl.devoxx.prezentatr.feed
import com.wordnik.swagger.annotations.Api
import com.wordnik.swagger.annotations.ApiOperation
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.PUT
import static pl.devoxx.prezentatr.config.Versions.PREZENTATR_JSON_VERSION_1

@Slf4j
@RestController
@RequestMapping('/feed')
@TypeChecked
@Api(value = "feed", description = "Collects changes in the brewing states")
class FeedController {

    private FeedRepository feedRepository

    @Autowired
    FeedController(FeedRepository feedRepository) {
        this.feedRepository = feedRepository
    }

    @RequestMapping(
            value = "/dojrzewatr/{id}",
            produces = PREZENTATR_JSON_VERSION_1,
            consumes = PREZENTATR_JSON_VERSION_1,
            method = PUT)
    @ApiOperation(value = "sends an order to dojrzewatr")
    public String dojrzewatr(@PathVariable String id) {
        log.info("new dojrzewatr id: ${id}")
        feedRepository.addModifyProcess(id, ProcessState.DOJRZEWATR)
    }

    @RequestMapping(
            value = "/butelkatr/{id}",
            produces = PREZENTATR_JSON_VERSION_1,
            consumes = PREZENTATR_JSON_VERSION_1,
            method = PUT)
    @ApiOperation(value = "sends an order to butelkatr")
    public String butelkatr(@PathVariable String id) {
        log.info("new butelkatr id: ${id}")
        feedRepository.addModifyProcess(id, ProcessState.BUTELKATR)
    }

    @RequestMapping(
            value = "/bottles/{bottles}",
            produces = PREZENTATR_JSON_VERSION_1,
            consumes = PREZENTATR_JSON_VERSION_1,
            method = PUT)
    @ApiOperation(value = "sets number of bottles")
    public String bottles(@PathVariable Integer bottles) {
        log.info("bottles number: ${bottles}")
        feedRepository.setBottles(bottles)
    }

    @RequestMapping(
            method = GET
    )
    public String show() {
        return feedRepository.showStatuses()
    }
}
