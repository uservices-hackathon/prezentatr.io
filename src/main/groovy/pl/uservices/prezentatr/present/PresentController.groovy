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
import pl.uservices.prezentatr.feed.FeedRepository
import pl.uservices.prezentatr.feed.ProcessState

import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.POST

@Slf4j
@RestController
@RequestMapping('/present')
@TypeChecked
class PresentController {

    private FeedRepository feedRepository

    private Trace trace

    private AggregatrClient aggregatrClient

    @Autowired
    public PresentController(FeedRepository feedRepository, Trace trace, AggregatrClient aggregatrClient) {
        this.feedRepository = feedRepository
        this.trace = trace
        this.aggregatrClient = aggregatrClient
    }

    @RequestMapping(
            value = "/order",
            method = POST)
    public String order(HttpEntity<String> body) {
        log.info("Making new order with $body.body")
        TraceScope scope = this.trace.startSpan("calling_aggregatr",
                new AlwaysSampler(), null)
        String result = aggregatrClient.ingredients
        scope.close()
        return result
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
