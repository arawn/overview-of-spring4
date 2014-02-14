package jco.conference.example.rest.github;

import jco.conference.example.rest.github.model.Follower;
import jco.conference.example.rest.github.model.Organization;
import jco.conference.example.rest.github.model.Repository;
import jco.conference.example.rest.github.model.UserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DefaultGithubService implements GithubService {

    private static final String GITHUB_API_USERS_REPOS = "https://api.github.com/users/{user}/repos";
    private static final String GITHUB_API_USERS_ORGS = "https://api.github.com/users/{user}/orgs";
    private static final String GITHUB_API_USERS_FOLLOWERS = "https://api.github.com/users/{user}/followers";

    private AsyncRestTemplate asyncRestTemplate;

    @Override
    public UserDetails loadUserDetails(String userId) {
        UserDetails.Builder builder = new UserDetails.Builder(userId);

        CountDownLatchCallback countDownLatchCallback = new CountDownLatchCallback(3);

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("user", userId);

        loadRepositories(builder, countDownLatchCallback, uriVariables);
        loadOrganizations(builder, countDownLatchCallback, uriVariables);
        loadFollowers(builder, countDownLatchCallback, uriVariables);

        try {
            if(countDownLatchCallback.await(3000, TimeUnit.MILLISECONDS)) {
                return builder.build();
            }
        } catch (InterruptedException ignore) {
        }

        return null;
    }

    private void loadRepositories(final UserDetails.Builder builder, CountDownLatchCallback countDownLatchCallback, Map<String, String> uriVariables) {
        ListenableFuture<ResponseEntity<Repository[]>> reposForEntity
                = asyncRestTemplate.getForEntity(GITHUB_API_USERS_REPOS, Repository[].class, uriVariables);
        reposForEntity.addCallback(new ListenableFutureCallback<ResponseEntity<Repository[]>>() {
            @Override
            public void onSuccess(ResponseEntity<Repository[]> result) {
                builder.setRepositories(new HashSet<>(Arrays.asList(result.getBody())));
            }
            @Override
            public void onFailure(Throwable t) {
                // 오류처리
            }
        });
        reposForEntity.addCallback(countDownLatchCallback);
    }

    private void loadOrganizations(final UserDetails.Builder builder, CountDownLatchCallback countDownLatchCallback, Map<String, String> uriVariables) {
        ListenableFuture<ResponseEntity<Organization[]>> orgsForEntity
                = asyncRestTemplate.getForEntity(GITHUB_API_USERS_ORGS, Organization[].class, uriVariables);
        orgsForEntity.addCallback(new ListenableFutureCallback<ResponseEntity<Organization[]>>() {
            @Override
            public void onSuccess(ResponseEntity<Organization[]> result) {
                builder.setOrganizations(new HashSet<>(Arrays.asList(result.getBody())));
            }
            @Override
            public void onFailure(Throwable t) {
                // 오류처리
            }
        });
        orgsForEntity.addCallback(countDownLatchCallback);
    }

    private void loadFollowers(final UserDetails.Builder builder, CountDownLatchCallback countDownLatchCallback, Map<String, String> uriVariables) {
        ListenableFuture<ResponseEntity<Follower[]>> followersForEntity
                = asyncRestTemplate.getForEntity(GITHUB_API_USERS_FOLLOWERS, Follower[].class, uriVariables);
        followersForEntity.addCallback(new ListenableFutureCallback<ResponseEntity<Follower[]>>() {
            @Override
            public void onSuccess(ResponseEntity<Follower[]> result) {
                builder.setFollowers(new HashSet<>(Arrays.asList(result.getBody())));
            }
            @Override
            public void onFailure(Throwable t) {
                // 오류처리
            }
        });
        followersForEntity.addCallback(countDownLatchCallback);
    }

    public void setAsyncRestTemplate(AsyncRestTemplate asyncRestTemplate) {
        this.asyncRestTemplate = asyncRestTemplate;
    }


    class CountDownLatchCallback implements ListenableFutureCallback<Object> {

        private final CountDownLatch lock;
        CountDownLatchCallback(int count) {
            this.lock = new CountDownLatch(count);
        }

        @Override
        public void onSuccess(Object result) {
            lock.countDown();
        }

        @Override
        public void onFailure(Throwable t) {
            lock.countDown();
        }

        public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
            return lock.await(timeout, unit);
        }
    }

}
