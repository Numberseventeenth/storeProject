package cn.store.service;

import cn.store.domain.Order;
import cn.store.domain.PageModel;
import cn.store.domain.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    void saveOrder(Order order) throws SQLException;

    PageModel findMyOrdersWithPage(User user, int curNum) throws SQLException, InvocationTargetException, IllegalAccessException;

    Order findOrderByOid(String oid) throws IllegalAccessException, SQLException, InvocationTargetException;

    void updataOrder(Order order) throws SQLException;

    List<Order> findAllOrders();

    List<Order> findAllOrders(String st);
}
