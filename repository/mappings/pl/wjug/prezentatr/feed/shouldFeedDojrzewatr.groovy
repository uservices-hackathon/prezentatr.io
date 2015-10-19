io.codearte.accurest.dsl.GroovyDsl.make {
    request {
        method 'PUT'
        url $(client(regex('^/feed/dojrzewatr')), server('/feed/dojrzewatr'))
        headers {
            header 'Content-Type': 'application/vnd.pl.devoxx.prezentatr.v1+json'
        }
    }
    response {
        status 200
    }
}