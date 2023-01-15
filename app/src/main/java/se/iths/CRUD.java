package se.iths;

import java.util.Collection;

public interface CRUD <T> {
    public Collection<T> findAll();
    public T findById();
    public T create(T object);
    public T update(T object);
    public boolean delete(T object);
}
