package jco.conference.example.rest.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Follower {

    private int id;
    private String login;
    private String url;

    public int getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Follower{");
        sb.append("id=").append(id);
        sb.append(", login='").append(login).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
