package fi.solita.indexer

import groovyx.net.http.RESTClient
import spock.lang.*

import spock.lang.Specification

class SearchSpec extends Specification {

//    def "Call index endpoint"() {
//        given: "REST client"
//        def client = new RESTClient("http://localhost:8080")
//
//        when: "we attempt to call /api/index endpoint"
//        def resp = client.get(path : "/api/index")
//
//        then: "we should get a valid JSON response"
//        with(resp) {
//            status == 200
//            data.text == "OK"
//        }
//
//    }

    def "Call search endpoint"() {
        given: " REST client"
        def client = new RESTClient("http://localhost:8080")

        when: "we attempt to call /api/search endpoint"
        def resp = client.get(path : "/api/search", query: ['query':'java'])

        then: "we should get a valid JSON response"
        with (resp) {
            status == 200
            !data.isEmpty()
        }
    }
}