package Services;

import java.util.List;


public interface IService<T>
{
    T get(int _id);
    List<T> getAll();
    int save(T _t);
    boolean delete(int _id);
}
