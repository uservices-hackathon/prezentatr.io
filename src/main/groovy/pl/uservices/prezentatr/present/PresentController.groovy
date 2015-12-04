package pl.uservices.prezentatr.present
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.sleuth.Trace
import org.springframework.cloud.sleuth.TraceManager
import org.springframework.cloud.sleuth.sampler.AlwaysSampler
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import pl.uservices.prezentatr.config.Versions
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

    private RestTemplate restTemplate

    @Autowired
    public PresentController(FeedRepository feedRepository, TraceManager traceManager,
                             AggregationServiceClient aggregationServiceClient, @LoadBalanced RestTemplate restTemplate) {
        this.feedRepository = feedRepository
        this.traceManager = traceManager
        this.aggregationServiceClient = aggregationServiceClient
        this.restTemplate = restTemplate
    }

    @RequestMapping(
            value = "/order",
            method = POST)
    public String order(HttpEntity<String> body) {
        log.info("Making new order with $body.body")
        Trace trace = this.traceManager.startSpan("calling_aggregation",
                new AlwaysSampler(), null)
        String result = useRestTemplateToCallAggregation(body, trace)
        traceManager.close(trace)
        return result
    }

    //TODO: Toggle on property or sth
    private String useRestTemplateToCallAggregation(HttpEntity<String> body, Trace trace) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("PROCESS-ID", trace.getSpan().getTraceId());
        headers.add("Content-Type", Versions.AGGREGATING_CONTENT_TYPE_V1);
        String serviceName = "aggregating";
        String url = "ingredients"
        URI uri = URI.create("http://" + serviceName + "/" + url);
        HttpMethod method = HttpMethod.POST
        String bodyAsString = body.body
        RequestEntity<String> requestEntity = new RequestEntity<>(bodyAsString, headers, method, uri);
        return restTemplate.exchange(requestEntity, String.class).body;
    }

    private String useFeignToCallAggregation(HttpEntity<String> body, Trace trace) {
        return aggregationServiceClient.getIngredients(body.body, trace.getSpan().getTraceId())
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
