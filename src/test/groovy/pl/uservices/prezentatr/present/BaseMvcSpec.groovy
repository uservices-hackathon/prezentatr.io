package pl.uservices.prezentatr.present

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc
import pl.uservices.prezentatr.feed.FeedRepository
import pl.uservices.prezentatr.feed.FeedController
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