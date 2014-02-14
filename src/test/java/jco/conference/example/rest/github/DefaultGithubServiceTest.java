package jco.conference.example.rest.github;

import jco.conference.example.rest.github.model.UserDetails;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.AsyncRestTemplate;

import static org.junit.Assert.assertNotNull;

public class DefaultGithubServiceTest {

    private DefaultGithubService githubService;

    @Before
    public void setup() {
        this.githubService = new DefaultGithubService();
        this.githubService.setAsyncRestTemplate(new AsyncRestTemplate());
    }

    @Test
    public void testLoadUserDetails() throws Exception {
        UserDetails userDetails = githubService.loadUserDetails("arawn");

        assertNotNull(userDetails);
        System.out.println(userDetails);
    }

}
