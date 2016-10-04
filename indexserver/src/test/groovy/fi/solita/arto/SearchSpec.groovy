package fi.solita.arto

import groovyx.net.http.RESTClient
import spock.lang.*

import spock.lang.Specification

class SearchSpec extends Specification {

    def "Call search endpoint"() {
//        given: "a valid account"
//        def authenticationTokenRequestParams = ['key':"AAABBBCCC123", 'user':"myauthemail@bla.com"]

        given: " REST client"
        def client = new RESTClient("http://localhost:8080/")

        when: "we attempt to call /search endpoint"
        def resp = client.get(path : "/api/search")

        then: "we should get a valid JSON response"
        println(resp.data);
        assert resp.data.isEmpty() == false

    }
}