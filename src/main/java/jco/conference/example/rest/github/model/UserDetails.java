package jco.conference.example.rest.github.model;

import java.util.Collections;
import java.util.Set;

public class UserDetails {

    private String id;
    private Set<Organization> organizations;
    private Set<Repository> repositories;
    private Set<Follower> followers;

    public UserDetails(String id, Set<Organization> organizations, Set<Repository> repositories, Set<Follower> followers) {
        this.id = id;
        this.organizations = organizations;
        this.repositories = repositories;
        this.followers = followers;
    }

    public String getId() {
        return id;
    }

    public Set<Organization> getOrganizations() {
        return Collections.unmodifiableSet(organizations);
    }

    public Set<Repository> getRepositories() {
        return Collections.unmodifiableSet(repositories);
    }

    public Set<Follower> getFollowers() {
        return Collections.unmodifiableSet(followers);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDetails{");
        sb.append("id='").append(id).append('\'');
        sb.append(", organizations=").append(organizations);
        sb.append(", repositories=").append(repositories);
        sb.append(", followers=").append(followers);
        sb.append('}');
        return sb.toString();
    }


    public static class Builder {

        private String id;
        private Set<Organization> organizations = Collections.emptySet();
        private Set<Repository> repositories = Collections.emptySet();
        private Set<Follower> followers = Collections.emptySet();

        public Builder(String id) {
            this.id = id;
        }

        public Builder setOrganizations(Set<Organization> organizations) {
            this.organizations = organizations;
            return this;
        }

        public Builder setRepositories(Set<Repository> repositories) {
            this.repositories = repositories;
            return this;
        }

        public Builder setFollowers(Set<Follower> followers) {
            this.followers = followers;
            return this;
        }

        public UserDetails build() {
            return new UserDetails(id, organizations, repositories, followers);
        }
    }

}
