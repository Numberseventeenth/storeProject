package cn.store.dao;

import cn.store.domain.Order;
import cn.store.domain.OrderItem;
import cn.store.domain.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    void saveOrder(Connection conn, Order order) throws SQLException;

    void saveOrderItem(Connection conn, OrderItem item) throws SQLException;

    int getTotalRecords(User user) throws SQLException;

    List findOrdersWithPage(User user, int startIndex, int pageSize) throws SQLException, InvocationTargetException, IllegalAccessException;

    Order findOrderByOid(String oid) throws SQLException, InvocationTargetException, IllegalAccessException;

    void updateOrder(Order order) throws SQLException;

    List<Order> findAllOrders() throws SQLException;

    List<Order> findAllOrders(String st) throws SQLException;
}
