package pl.uservices.prezentatr.present
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc
import spock.lang.Specification

class BaseMvcSpec extends Specification {

     def setup() {
        setupMocks()
        RestAssuredMockMvc.standaloneSetup()
    }

    void setupMocks() {
    }



}