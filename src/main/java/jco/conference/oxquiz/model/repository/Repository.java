package jco.conference.oxquiz.model.repository;

public interface Repository<T> {

    void save(T entity);

    void remove(T entity);

    Iterable<T> findAll();

}
