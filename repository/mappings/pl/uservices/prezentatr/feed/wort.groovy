io.codearte.accurest.dsl.GroovyDsl.make {
	request {
		method "PUT"
		url "/wort"
		body( '{ "warehouseState": 1000 }' )
		headers { header("Content-Type", "application/prezentator.v1+json") }
	}
	response {
		status 204
	}
}