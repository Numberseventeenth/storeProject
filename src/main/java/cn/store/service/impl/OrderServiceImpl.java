package cn.store.service.impl;

import cn.store.dao.OrderDao;
import cn.store.domain.Order;
import cn.store.domain.OrderItem;
import cn.store.domain.PageModel;
import cn.store.domain.User;
import cn.store.service.OrderService;
import cn.store.utils.BeanFactory;
import cn.store.utils.JDBCUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    OrderDao orderDao = (OrderDao) BeanFactory.createObject("orderDao");

    @Override
    public void saveOrder(Order order) throws SQLException {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            orderDao.saveOrder(conn,order);

            for (OrderItem item : order.getList()) {
                orderDao.saveOrderItem(conn,item);
            }
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
        }
    }

    @Override
    public PageModel findMyOrdersWithPage(User user, int curNum) throws SQLException, InvocationTargetException, IllegalAccessException {
        int totalRecords = orderDao.getTotalRecords(user);
        PageModel pm = new PageModel(curNum,totalRecords,3);
        List list = orderDao.findOrdersWithPage(user,pm.getStartIndex(),pm.getPageSize());
        pm.setList(list);
        pm.setUrl("OrderServlet?method=findMyOrdersWithPage");
        return pm;
    }

    @Override
    public Order findOrderByOid(String oid) throws IllegalAccessException, SQLException, InvocationTargetException {
        return orderDao.findOrderByOid(oid);
    }

    @Override
    public void updataOrder(Order order) throws SQLException {
        orderDao.updateOrder(order);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderDao.findAllOrders();
    }

    @Override
    public List<Order> findAllOrders(String st) {
        return orderDao.findAllOrders(st);
    }
}
