io.codearte.accurest.dsl.GroovyDsl.make {
	request {
		method "POST"
		url "/bottle"
		body( '{ "quantity":1000 }' )
		headers { header("Content-Type", "application/prezentator.v1+json") }
	}
	response {
		status 204
	}
}