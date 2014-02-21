package jco.conference.example.rest.social;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.AsyncRestTemplate;

public class SharedCountTest {

    private AsyncRestTemplate asyncRestTemplate;
    private SharedCount sharedCount;

    @Before
    public void setup() {
        this.asyncRestTemplate = new AsyncRestTemplate();
        this.sharedCount = new SharedCount(asyncRestTemplate);
        this.sharedCount.setGoogleApiKey("AIzaSyCKSbrvQasunBoV16zDH9R33D88CeLr9gQ");
    }

    @Test
    public void sharedCount() {
        long count = sharedCount.inquire("http://conference.jco.or.kr/");
        System.out.println("count = " + count);
    }

}
