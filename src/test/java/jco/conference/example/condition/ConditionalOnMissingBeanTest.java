package jco.conference.example.condition;

import jco.conference.example.rest.github.DefaultGithubService;
import jco.conference.example.rest.github.GithubService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConditionalOnMissingBeanTest.ConditionalOnMissingBeanTestConfig.class)
public class ConditionalOnMissingBeanTest {

    @Autowired(required = false)
    private GithubService githubService;

    @Test
    public void test() throws Exception {
        assertTrue(githubService instanceof MockGithubService);
    }

    @Configuration
    static class ConditionalOnMissingBeanTestConfig {

        /**
         * @Bean 이 없기 때문에 실제 Spring 빈으로 등록되지 않는다.
         */
        public GithubService defaultGithubService() {
            return new DefaultGithubService();
        }

        @Bean
        @ConditionalOnMissingBean(GithubService.class)
        public GithubService mockGithubService() {
            return new MockGithubService();
        }

    }

}
