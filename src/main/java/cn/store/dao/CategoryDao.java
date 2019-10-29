package cn.store.dao;

import cn.store.domain.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {
    List<Category> findAllCates() throws SQLException;

    void addCategory(Category category) throws SQLException;
}
