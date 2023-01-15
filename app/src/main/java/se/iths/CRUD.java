package se.iths;

import java.util.Collection;

public interface CRUD <T> {
    public Collection<T> findAll();
    public T findById(int id);
    public void create(T object);
    public void update(T object);
    public void delete(T object);
}
