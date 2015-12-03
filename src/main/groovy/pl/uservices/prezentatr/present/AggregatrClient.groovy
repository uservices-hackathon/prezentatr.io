package pl.uservices.prezentatr.present

import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import pl.uservices.prezentatr.config.Versions

@FeignClient("aggregatr")
@RequestMapping(value = "/ingredients", consumes = Versions.AGREGATR_CONTENT_TYPE_V1, produces = MediaType.APPLICATION_JSON_VALUE)
interface AggregatrClient {

    @RequestMapping(method = RequestMethod.POST)
    String getIngredients()
}