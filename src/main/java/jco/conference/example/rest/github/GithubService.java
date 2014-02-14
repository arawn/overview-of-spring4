package jco.conference.example.rest.github;

import jco.conference.example.rest.github.model.UserDetails;

public interface GithubService {

    UserDetails loadUserDetails(String userId);

}
