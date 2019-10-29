package cn.store.dao;

import cn.store.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    List<Product> findHots() throws SQLException;

    List<Product> findNews() throws SQLException;

    Product findProductByPid(String pid) throws SQLException;

    int findTotalRecords(String cid) throws SQLException;

    List<Product> findProductByCidWithPage(String cid, int startIndex, int endPage) throws SQLException;

    int findTotalRecords()throws Exception;

    List<Product> findProductByCidWithPage(int startIndex, int pageSize) throws SQLException;

    void saveProduct(Product product) throws SQLException;
}
