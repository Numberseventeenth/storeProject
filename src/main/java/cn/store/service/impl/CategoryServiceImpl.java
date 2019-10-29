package cn.store.service.impl;

import cn.store.dao.CategoryDao;
import cn.store.dao.impl.CategoryDaoImpl;
import cn.store.domain.Category;
import cn.store.service.CategoryService;
import cn.store.utils.BeanFactory;
import cn.store.utils.JedisUtils;
import redis.clients.jedis.Jedis;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    CategoryDao categoryDao = (CategoryDao) BeanFactory.createObject("categoryDao");

    @Override
    public List<Category> getAllCates() throws SQLException {
        return categoryDao.findAllCates();
    }

    @Override
    public void addCategory(Category category) throws SQLException {
        categoryDao.addCategory(category);

        Jedis jedis = JedisUtils.getJedis();
        jedis.del("allCats");
        JedisUtils.closeJedis(jedis);
    }
}
