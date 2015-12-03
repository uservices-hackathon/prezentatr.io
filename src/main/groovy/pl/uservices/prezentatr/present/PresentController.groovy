package pl.uservices.prezentatr.present

import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.sleuth.Trace
import org.springframework.cloud.sleuth.TraceScope
import org.springframework.cloud.sleuth.sampler.AlwaysSampler
import org.springframework.http.HttpEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.uservices.prezentatr.config.Collaborators
import pl.uservices.prezentatr.feed.FeedRepository
import pl.uservices.prezentatr.feed.ProcessState

import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.POST
import static pl.uservices.prezentatr.config.Versions.AGREGATR_CONTENT_TYPE_V1

@Slf4j
@RestController
@RequestMapping('/present')
@TypeChecked
class PresentController {

    private FeedRepository feedRepository

    private Trace trace

    @Autowired
    public PresentController(FeedRepository feedRepository, Trace trace) {
        this.feedRepository = feedRepository
        this.trace = trace
    }

    @RequestMapping(
            value = "/order",
            method = POST)
    public String order(HttpEntity<String> body) {
        log.info("Making new order with $body.body")
        TraceScope scope = this.trace.startSpan("calling_aggregatr",
                new AlwaysSampler(), null);
        /*String result = restClient.forService(Collaborators.AGGREGATR_DEPENDENCY_NAME).post().onUrl("/ingredients")
                .body(body.body)
                    .withHeaders().contentType(AGREGATR_CONTENT_TYPE_V1)
                .andExecuteFor()
                .anObject()
                .ofType(String)
        */scope.close()
        return ''//result
    }

    @RequestMapping(value = "/dojrzewatr", method = GET)
    public String dojrzewatr() {
        return feedRepository.countFor(ProcessState.DOJRZEWATR)
    }

    @RequestMapping(value = "/butelkatr", method = GET)
    public String butelkatr() {
        return feedRepository.countFor(ProcessState.BUTELKATR)
    }

    @RequestMapping(value = "/bottles", method = GET)
    public String bottles() {
        return feedRepository.getBottles()
    }
}
