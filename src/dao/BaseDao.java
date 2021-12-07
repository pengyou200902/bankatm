/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:40
 */

package dao;

import java.util.List;

public interface BaseDao<T, K> {

    boolean save(T model);

    T getById(K id);

    List<T> getAll();

    boolean update(T model);

    boolean delete(T model);
}
