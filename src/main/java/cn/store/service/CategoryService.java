package cn.store.service;

import cn.store.domain.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCates() throws SQLException;

    void addCategory(Category category) throws SQLException;
}
