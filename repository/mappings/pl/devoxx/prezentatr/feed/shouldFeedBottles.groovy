io.coderate.accurest.dsl.GroovyDsl.make {
    request {
        method 'PUT'
        url $(client(regex('^/feed/bottles/\\d$')), server('/feed/bottles/34'))
        headers {
            header 'Content-Type': 'application/vnd.pl.devoxx.prezentatr.v1+json'
        }
    }
    response {
        status 200
    }
}