package jco.conference.example.condition;

import jco.conference.example.rest.github.GithubService;
import jco.conference.example.rest.github.model.UserDetails;

public class MockGithubService implements GithubService {

    @Override
    public UserDetails loadUserDetails(String userId) {
        return null;
    }

}
