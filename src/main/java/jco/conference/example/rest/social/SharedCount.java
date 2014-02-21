package jco.conference.example.rest.social;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.LongAccumulator;

/**
 * 각종 SNS에 공유된 URL 횟수를 조회한다.
 *
 * 지원 SNS:
 *   - Facebook
 *   - Twitter
 *   - Google Plus
 *
 * @author arawn.kr@gmail.com
 */
public class SharedCount {

    private static final String GOOGLE_PLUS_API = "https://clients6.google.com/rpc?key={googleApiKey}";
    private static final String FACEBOOK_API = "http://graph.facebook.com/?id={httpUrl}";
    private static final String TWITTER_API = "http://cdn.api.twitter.com/1/urls/count.json?url={httpUtl}";


    private AsyncRestTemplate asyncRestTemplate;
    private URI googleSharedCountApiURI;

    public SharedCount(AsyncRestTemplate asyncRestTemplate) {
        setAsyncRestTemplate(asyncRestTemplate);
    }

    public long inquire(String httpUrl) {
        LongAccumulator accumulator = new LongAccumulator((left, right) -> left + right, 0);
        CountDownLatchCallback countDownLatchCallback = new CountDownLatchCallback(3);

        countFromFacebook(httpUrl, accumulator, countDownLatchCallback);
        countFromTwitter(httpUrl, accumulator, countDownLatchCallback);
        countFromGooglePlus(httpUrl, accumulator, countDownLatchCallback);

        try {
            countDownLatchCallback.await();
        } catch (InterruptedException ignore) {
            throw new RuntimeException("URL 공유 횟수 조회 중 문제가 발생했습니다.");
        }

        return accumulator.get();
    }

    private void countFromFacebook(String httpUrl, LongAccumulator accumulator, CountDownLatchCallback countDownLatchCallback) {
        ListenableFuture<ResponseEntity<ObjectNode>> future = asyncRestTemplate.getForEntity(FACEBOOK_API, ObjectNode.class, httpUrl);

        future.addCallback(new CountAccumulatorCallback(accumulator, "shares"));
        future.addCallback(countDownLatchCallback);
    }

    private void countFromTwitter(String httpUrl, LongAccumulator accumulator, CountDownLatchCallback countDownLatchCallback) {
        ListenableFuture<ResponseEntity<ObjectNode>> future = asyncRestTemplate.getForEntity(TWITTER_API, ObjectNode.class, httpUrl);

        future.addCallback(new CountAccumulatorCallback(accumulator, "count"));
        future.addCallback(countDownLatchCallback);
    }

    private void countFromGooglePlus(String httpUrl, LongAccumulator accumulator, CountDownLatchCallback countDownLatchCallback) {
        if(Objects.isNull(googleSharedCountApiURI)) {
            countDownLatchCallback.countDown();
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("id", httpUrl);
        params.put("nolog", true);
        params.put("source", "widget");
        params.put("userId", "@viewer");
        params.put("groupId", "@self");

        Map<String, Object> body = new HashMap<>();
        body.put("method", "pos.plusones.get");
        body.put("id", "p");
        body.put("params", params);
        body.put("jsonrpc", "2.0");
        body.put("key", "p");
        body.put("apiVersion", "v1");

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body);
        ListenableFuture<ResponseEntity<ObjectNode>> future = asyncRestTemplate.postForEntity(googleSharedCountApiURI, httpEntity, ObjectNode.class);

        future.addCallback(new CountAccumulatorCallback(accumulator, "count"));
        future.addCallback(countDownLatchCallback);
    }

    public void setAsyncRestTemplate(AsyncRestTemplate asyncRestTemplate) {
        this.asyncRestTemplate = asyncRestTemplate;
    }

    public void setGoogleApiKey(String googleApiKey) {
        this.googleSharedCountApiURI = UriComponentsBuilder.fromHttpUrl(GOOGLE_PLUS_API)
                                                           .buildAndExpand(googleApiKey)
                                                           .toUri();
    }


    class CountAccumulatorCallback implements ListenableFutureCallback<ResponseEntity<ObjectNode>> {

        final LongAccumulator accumulator;
        final String countFieldName;
        CountAccumulatorCallback(LongAccumulator accumulator, String countFieldName) {
            this.accumulator = accumulator;
            this.countFieldName = countFieldName;
        }

        @Override
        public void onSuccess(ResponseEntity<ObjectNode> response) {
            accumulator.accumulate(response.getBody().findPath(countFieldName).asLong());
        }

        @Override
        public void onFailure(Throwable t) {
            // nothing
        }
    }

    class CountDownLatchCallback implements ListenableFutureCallback<Object> {

        final CountDownLatch lock;
        CountDownLatchCallback(int count) {
            this.lock = new CountDownLatch(count);
        }

        @Override
        public void onSuccess(Object result) {
            countDown();
        }

        @Override
        public void onFailure(Throwable t) {
            countDown();
        }

        void countDown() {
            lock.countDown();
        }

        void await() throws InterruptedException {
            lock.await();
        }
    }

}
