package pl.devoxx.prezentatr.acceptance

import groovy.json.JsonSlurper
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MvcResult
import pl.devoxx.prezentatr.base.MicroserviceMvcWiremockSpec

import static java.net.URI.create
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

@ContextConfiguration
class AcceptanceSpec extends MicroserviceMvcWiremockSpec {

    def 'should call aggregatr to order ingredients'() {
        when:
            MvcResult result = order_ingredients()
        then:
            aggregated_ingredients_are_present(result)
    }

    private MvcResult order_ingredients() {
        return mockMvc.perform(post(create('/present/order')).content("""{"items":["WATER"]}""")).andDo(print()).andReturn()
    }

    private void aggregated_ingredients_are_present(MvcResult result) {
        assert !result.resolvedException
        Map parsedResult = new JsonSlurper().parseText(result.response.contentAsString)
        Map expectedResult = new JsonSlurper().parseText('''
                {
                    "ingredients": [
                            {"type":"WATER","quantity":200}
                        ]
                }
            ''')
       assert parsedResult == expectedResult
    }

}
