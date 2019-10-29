package cn.store.service;

import cn.store.domain.PageModel;
import cn.store.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    List<Product> findHots() throws SQLException;

    List<Product> findNews() throws SQLException;

    Product findProductByPid(String pid) throws SQLException;

    PageModel findProductByCidWithPage(String cid, int curNum) throws SQLException;

    PageModel findAllProductWithPage(int curNum)throws Exception;

    void saveProduct(Product product)throws Exception;
}
