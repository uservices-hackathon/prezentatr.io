package pl.devoxx.prezentatr.present

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc
import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import org.springframework.beans.factory.annotation.Autowired
import pl.devoxx.prezentatr.feed.FeedController
import pl.devoxx.prezentatr.feed.FeedRepository
import spock.lang.Specification

class BaseMvcSpec extends Specification {

    FeedRepository feedRepository = new FeedRepository()

    def setup() {
        setupMocks()
        RestAssuredMockMvc.standaloneSetup(new FeedController(feedRepository))
    }

    void setupMocks() {
    }



}