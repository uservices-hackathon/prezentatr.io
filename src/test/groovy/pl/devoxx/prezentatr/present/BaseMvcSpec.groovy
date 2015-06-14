package pl.devoxx.prezentatr.present

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc
import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

class BaseMvcSpec extends Specification {

    @Autowired ServiceRestClient restClient

    def setup() {
        setupMocks()
        RestAssuredMockMvc.standaloneSetup(new PresentController(restClient))
    }

    void setupMocks() {

    }



}