io.codearte.accurest.dsl.GroovyDsl.make {
    request {
        method 'PUT'
        url $(client(regex('^/feed/butelkatr')), server('/feed/butelkatr'))
        headers {
            header 'Content-Type': 'application/vnd.pl.uservices.prezentatr.v1+json'
        }
    }
    response {
        status 200
    }
}