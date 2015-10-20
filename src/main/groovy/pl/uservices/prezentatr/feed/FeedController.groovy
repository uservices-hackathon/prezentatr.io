package pl.uservices.prezentatr.feed

import com.ofg.infrastructure.correlationid.CorrelationIdHolder
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
import static pl.uservices.prezentatr.config.Versions.PREZENTATR_JSON_VERSION_1

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
            value = "/dojrzewatr",
            produces = PREZENTATR_JSON_VERSION_1,
            consumes = PREZENTATR_JSON_VERSION_1,
            method = PUT)
    @ApiOperation(value = "sends an order to dojrzewatr")
    public String dojrzewatr() {
        log.info("new dojrzewatr")
        feedRepository.addModifyProcess(CorrelationIdHolder.get(), ProcessState.DOJRZEWATR)
    }

    @RequestMapping(
            value = "/butelkatr",
            produces = PREZENTATR_JSON_VERSION_1,
            consumes = PREZENTATR_JSON_VERSION_1,
            method = PUT)
    @ApiOperation(value = "sends an order to butelkatr")
    public String butelkatr() {
        log.info("new butelkatr")
        feedRepository.addModifyProcess(CorrelationIdHolder.get(), ProcessState.BUTELKATR)
    }

    @RequestMapping(
            value = "/bottles/{bottles}",
            produces = PREZENTATR_JSON_VERSION_1,
            consumes = PREZENTATR_JSON_VERSION_1,
            method = PUT)
    @ApiOperation(value = "sets number of bottles")
    public String bottles(@PathVariable Integer bottles) {
        log.info("bottles number: ${bottles}")
        feedRepository.setBottles(CorrelationIdHolder.get(), bottles)
    }

    @RequestMapping(
            method = GET
    )
    public String show() {
        return feedRepository.showStatuses()
    }
}
