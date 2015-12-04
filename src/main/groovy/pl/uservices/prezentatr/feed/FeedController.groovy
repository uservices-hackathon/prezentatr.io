package pl.uservices.prezentatr.feed
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.PUT
import static pl.uservices.prezentatr.config.Versions.PRESENTING_JSON_VERSION_1

@Slf4j
@RestController
@RequestMapping('/feed')
@TypeChecked
class FeedController {

    private FeedRepository feedRepository

    @Autowired
    FeedController(FeedRepository feedRepository) {
        this.feedRepository = feedRepository
    }

    @RequestMapping(
            value = "/maturing",
            produces = PRESENTING_JSON_VERSION_1,
            consumes = PRESENTING_JSON_VERSION_1,
            method = PUT)
    public String maturing(@RequestHeader("PROCESS-ID") String processId) {
        log.info("new maturing")
        return feedRepository.addModifyProcess(processId, ProcessState.MATURING)
    }

    @RequestMapping(
            value = "/bottling",
            produces = PRESENTING_JSON_VERSION_1,
            consumes = PRESENTING_JSON_VERSION_1,
            method = PUT)
    public String bottling(@RequestHeader("PROCESS-ID") String processId) {
        log.info("new bottling")
        return feedRepository.addModifyProcess(processId, ProcessState.BOTTLING)
    }

    @RequestMapping(
            value = "/bottles/{bottles}",
            produces = PRESENTING_JSON_VERSION_1,
            consumes = PRESENTING_JSON_VERSION_1,
            method = PUT)
    public String bottles(@PathVariable Integer bottles, @RequestHeader("PROCESS-ID") String processId) {
        log.info("bottles number: ${bottles}")
        return feedRepository.setBottles(processId, bottles)
    }

    @RequestMapping(
            method = GET
    )
    public String show() {
        return feedRepository.showStatuses()
    }
}
