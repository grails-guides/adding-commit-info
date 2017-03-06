package demo

import spock.lang.Ignore
import spock.lang.Specification

class GitPropertiesSpec extends Specification {

    @Ignore // dependent on git user
    void "inspecting git.properties"() {
        given:
        Properties props = new Properties()
        File gitProperties = new File("build/resources/main/git.properties")

        when:
        props.load(gitProperties.newDataInputStream())

        then:
        props['git.branch'] == 'master'
        props['git.commit.user.name'] == "Colin Harrington"
        props['git.commit.user.email'] == "colin.harrington@gmail.com"
        props['git.commit.id'].matches('^[0-9a-f]+$')
        props['git.commit.id'].startsWith(props['git.commit.id.abbrev'])
        props['git.commit.time']
        props['git.commit.message.short']
        props['git.commit.message.full']
    }
}
