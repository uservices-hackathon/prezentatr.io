io.coderate.accurest.dsl.GroovyDsl.make {
    request {
        method 'PUT'
        url $(client(regex('^/feed/butelkatr/.+$')), server('/feed/butelkatr/774e2609-1b50-435a-b830-df9591aa1f97'))
        headers {
            header 'Content-Type': 'application/vnd.pl.devoxx.prezentatr.v1+json'
        }
    }
    response {
        status 200
    }
}