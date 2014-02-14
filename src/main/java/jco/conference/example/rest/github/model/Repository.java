package jco.conference.example.rest.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository {

    private int id;
    private String name;
    @JsonProperty("html_url")
    private String htmlUrl;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getHtmlUrl() {
        return htmlUrl;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Repository{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", htmlUrl='").append(htmlUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
