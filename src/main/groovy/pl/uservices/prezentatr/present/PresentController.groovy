package pl.uservices.prezentatr.present
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.sleuth.Trace
import org.springframework.cloud.sleuth.TraceManager
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

    private TraceManager traceManager

    private AggregatrClient aggregatrClient

    @Autowired
    public PresentController(FeedRepository feedRepository, TraceManager traceManager, AggregatrClient aggregatrClient) {
        this.feedRepository = feedRepository
        this.traceManager = traceManager
        this.aggregatrClient = aggregatrClient
    }

    @RequestMapping(
            value = "/order",
            method = POST)
    public String order(HttpEntity<String> body) {
        log.info("Making new order with $body.body")
        Trace trace = this.traceManager.startSpan("calling_aggregatr",
                new AlwaysSampler(), null)
        String result = aggregatrClient.getIngredients(body.body, trace.getSpan().getTraceId())
        traceManager.close(trace)
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
