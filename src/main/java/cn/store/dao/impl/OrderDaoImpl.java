package cn.store.dao.impl;

import cn.store.dao.OrderDao;
import cn.store.domain.Order;
import cn.store.domain.OrderItem;
import cn.store.domain.Product;
import cn.store.domain.User;
import cn.store.utils.JDBCUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void saveOrder(Connection conn, Order order) throws SQLException {
        String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        Object[] params = {order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()};
        qr.update(conn,sql,params);
    }

    @Override
    public void saveOrderItem(Connection conn, OrderItem item) throws SQLException {
        String sql = "insert into orderitem values(?,?,?,?,?)";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        Object[] params = {item.getItemid(),item.getQuantity(),item.getTotal(),item.getProduct().getPid(),item.getOrder().getOid()};
        qr.update(conn,sql,params);
    }

    @Override
    public int getTotalRecords(User user) throws SQLException {
        String sql = "select count(*) from orders where uid=?";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        Long num = (Long) qr.query(sql,new ScalarHandler(),user.getUid());
        return num.intValue();
    }

    @Override
    public List findOrdersWithPage(User user, int startIndex, int pageSize) throws SQLException, InvocationTargetException, IllegalAccessException {
        String sql = "select * from orders where uid=? limit ?,?";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        List<Order> list = qr.query(sql,new BeanListHandler<Order>(Order.class),user.getUid(),startIndex,pageSize);
        for (Order order : list) {
            String oid = order.getOid();
            sql = "select * from orderitem o,product p where o.pid=p.pid and oid=?";
            List<Map<String,Object>> list02 = qr.query(sql,new MapListHandler(),oid);
            for (Map<String, Object> map : list02) {
                OrderItem orderItem = new OrderItem();
                Product product = new Product();
                DateConverter dt = new DateConverter();
                dt.setPattern("yyyy-MM-dd");
                ConvertUtils.register(dt, Date.class);
                BeanUtils.populate(orderItem,map);
                BeanUtils.populate(product,map);

                orderItem.setProduct(product);
                order.getList().add(orderItem);
            }
        }
        return list;
    }

    @Override
    public Order findOrderByOid(String oid) throws SQLException, InvocationTargetException, IllegalAccessException {
        String sql = "select * from orders where oid=?";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        Order order = qr.query(sql,new BeanHandler<Order>(Order.class),oid);
        sql = "select * from orderitem o,product p where o.pid=p.pid and oid=?";
        List<Map<String, Object>> list02 = qr.query(sql, new MapListHandler(), oid);
        for (Map<String, Object> map : list02) {
            OrderItem orderItem = new OrderItem();
            Product product = new Product();

            DateConverter dt = new DateConverter();
            dt.setPattern("yyyy-MM-dd");
            ConvertUtils.register(dt,Date.class);
            BeanUtils.populate(orderItem,map);
            BeanUtils.populate(product,map);
        }
        return order;
    }

    @Override
    public void updateOrder(Order order) throws SQLException {
        String sql = "update orders set ordertime=?,total=?,state=?,address=?,name=?,telephone=? where oid=?";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        Object[] params = {order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid()};
        qr.update(sql,params);
    }

    @Override
    public List<Order> findAllOrders() throws SQLException {
        String sql = "select * from orders";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        return qr.query(sql,new BeanListHandler<Order>(Order.class));
    }

    @Override
    public List<Order> findAllOrders(String st) throws SQLException {
        String sql = "select * from orders where state=?";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        return qr.query(sql,new BeanListHandler<Order>(Order.class),st);
    }


}
