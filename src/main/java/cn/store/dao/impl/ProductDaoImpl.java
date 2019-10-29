package cn.store.dao.impl;

import cn.store.dao.ProductDao;
import cn.store.domain.Product;
import cn.store.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public List<Product> findHots() throws SQLException {
        String sql = "select * from product where pflag=0 order by pdate desc limit 0,9";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        return qr.query(sql,new BeanListHandler<Product>(Product.class));
    }

    @Override
    public List<Product> findNews() throws SQLException {
        String sql = "select * from product where pflag=0 and is_hot=1 order by pdate desc limit 0,9";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        return qr.query(sql,new BeanListHandler<Product>(Product.class));
    }

    @Override
    public Product findProductByPid(String pid) throws SQLException {
        String sql = "select * from product where pid=?";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        return qr.query(sql,new BeanHandler<Product>(Product.class),pid);
    }

    @Override
    public int findTotalRecords(String cid) throws SQLException {
        String sql = "select count(*) from product where cid=?";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        Long num = (Long) qr.query(sql,new ScalarHandler(),cid);
        return num.intValue();
    }

    @Override
    public List<Product> findProductByCidWithPage(String cid, int startIndex, int endPage) throws SQLException {
        String sql = "select * from product where cid=? limit ?,?";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        return qr.query(sql,new BeanListHandler<Product>(Product.class),cid,startIndex,endPage);
    }

    @Override
    public int findTotalRecords() throws Exception {
        String sql = "select count(*) from product";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        Long count = (Long) qr.query(sql,new ScalarHandler());
        return count.intValue();
    }

    @Override
    public List<Product> findProductByCidWithPage(int startIndex, int pageSize) throws SQLException {
        String sql = "select * from product order by pdate desc limit ?,?";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        return qr.query(sql,new BeanListHandler<Product>(Product.class),startIndex,pageSize);
    }

    @Override
    public void saveProduct(Product product) throws SQLException {
        String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        Object[] params = {product.getCid(),product.getPname(),product.getMarket_price(),product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_not(),product.getPdesc(),product.getPflag(),product.getCid()};
        qr.update(sql,params);
    }
}
