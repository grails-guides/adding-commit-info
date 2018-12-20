package demo

import grails.testing.mixin.integration.Integration
import grails.testing.spock.OnceBefore
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.DefaultHttpClient
import io.micronaut.http.client.DefaultHttpClientConfiguration
import io.micronaut.http.client.HttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import java.time.Duration

@Integration
class InfoSpec extends Specification {

    @Shared
    @AutoCleanup
    HttpClient client

    @Shared
    String baseUrl

    @OnceBefore
    void init() {
        this.baseUrl = "http://localhost:$serverPort"
        DefaultHttpClientConfiguration configuration = new DefaultHttpClientConfiguration()
        configuration.setReadTimeout(Duration.ofMinutes(5))
        this.client  = new DefaultHttpClient(new URL(baseUrl), configuration)
    }

    def "test git commit info appears in JSON"() {
        given:

        when:
        HttpResponse<Map> resp = client.toBlocking().exchange(HttpRequest.GET("/actuator/info"), Map)

        then:
        resp.status() == HttpStatus.OK
        resp.body()
        resp.body().git
        resp.body().git.commit
        resp.body().git.commit.message
        resp.body().git.commit.time
        resp.body().git.commit.id
        resp.body().git.commit.user
        resp.body().git.branch
    }
}
