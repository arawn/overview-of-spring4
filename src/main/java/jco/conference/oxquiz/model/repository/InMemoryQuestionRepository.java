package jco.conference.oxquiz.model.repository;

import jco.conference.oxquiz.model.Question;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class InMemoryQuestionRepository implements QuestionRepository {

    private Set<Question> entries = Collections.synchronizedSet(new LinkedHashSet<>());

    @Override
    public void save(Question entity) {
        entries.add(entity);
    }

    @Override
    public void remove(Question entity) {
        entries.remove(entity);
    }

    @Override
    public Iterable<Question> findAll() {
        return Collections.unmodifiableSet(entries);
    }

    @Override
    public Question findOneByLastCreatedDateTime() {
        Optional<Question> lastQuestion =
            entries.stream()
                   .max((source, target) -> source.getCreatedDateTime().compareTo(target.getCreatedDateTime()));

        return lastQuestion.isPresent() ? lastQuestion.get() : null;
    }

}
