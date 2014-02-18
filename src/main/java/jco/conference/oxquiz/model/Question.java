package jco.conference.oxquiz.model;

import java.time.LocalDateTime;

public class Question {

    private String content;
    private LocalDateTime createdDateTime;

    public Question(String content) {
        this.content = content;
        this.createdDateTime = LocalDateTime.now();
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;

        Question question = (Question) o;

        if (!content.equals(question.content)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Question{");
        sb.append("content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
