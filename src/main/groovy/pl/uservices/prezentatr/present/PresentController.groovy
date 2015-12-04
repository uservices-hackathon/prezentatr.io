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

    private AggregationServiceClient aggregationServiceClient

    @Autowired
    public PresentController(FeedRepository feedRepository, TraceManager traceManager, AggregationServiceClient aggregationServiceClient) {
        this.feedRepository = feedRepository
        this.traceManager = traceManager
        this.aggregationServiceClient = aggregationServiceClient
    }

    @RequestMapping(
            value = "/order",
            method = POST)
    public String order(HttpEntity<String> body) {
        log.info("Making new order with $body.body")
        Trace trace = this.traceManager.startSpan("calling_aggregation",
                new AlwaysSampler(), null)
        String result = aggregationServiceClient.getIngredients(body.body, trace.getSpan().getTraceId())
        traceManager.close(trace)
        return result
    }

    @RequestMapping(value = "/maturing", method = GET)
    public String dojrzewatr() {
        return feedRepository.countFor(ProcessState.MATURING)
    }

    @RequestMapping(value = "/bottling", method = GET)
    public String butelkatr() {
        return feedRepository.countFor(ProcessState.BOTTLING)
    }

    @RequestMapping(value = "/bottles", method = GET)
    public String bottles() {
        return feedRepository.getBottles()
    }
}
