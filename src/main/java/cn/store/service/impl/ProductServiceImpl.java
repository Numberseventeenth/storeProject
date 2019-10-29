package cn.store.service.impl;

import cn.store.dao.ProductDao;
import cn.store.domain.PageModel;
import cn.store.domain.Product;
import cn.store.service.ProductService;
import cn.store.utils.BeanFactory;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    ProductDao productDao = (ProductDao) BeanFactory.createObject("productDao");

    @Override
    public List<Product> findHots() throws SQLException {
        return productDao.findHots();
    }

    @Override
    public List<Product> findNews() throws SQLException {
        return productDao.findNews();
    }

    @Override
    public Product findProductByPid(String pid) throws SQLException {
        return productDao.findProductByPid(pid);
    }

    @Override
    public PageModel findProductByCidWithPage(String cid, int curNum) throws SQLException {
        int totalRecoreds = productDao.findTotalRecords(cid);
        PageModel pm = new PageModel(curNum,totalRecoreds,12);
        List<Product> list = productDao.findProductByCidWithPage(cid,pm.getStartIndex(),pm.getEndPage());
        System.out.println(list);
        pm.setList(list);
        pm.setUrl("ProductServlet?method=findProductByCidWithPage&cid=" + cid);
        return pm;
    }

    @Override
    public PageModel findAllProductWithPage(int curNum) throws Exception {
        int totalRecords = productDao.findTotalRecords();
        PageModel pm = new PageModel(curNum,totalRecords,5);
        List<Product> list = productDao.findProductByCidWithPage(pm.getStartIndex(),pm.getPageSize());
        pm.setList(list);
        pm.setUrl("AdminProductServlet?method=findAllProductWithPage");
        return pm;
    }

    @Override
    public void saveProduct(Product product) throws Exception {
        productDao.saveProduct(product);
    }


}
