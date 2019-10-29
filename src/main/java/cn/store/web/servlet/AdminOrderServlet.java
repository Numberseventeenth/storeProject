package cn.store.web.servlet;

import cn.store.domain.Order;
import cn.store.service.OrderService;
import cn.store.service.impl.OrderServiceImpl;
import cn.store.web.base.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {

    public String findOrders(HttpServletRequest request, HttpServletResponse response)throws Exception{
        OrderService orderService = new OrderServiceImpl();
        String st = request.getParameter("state");
        List<Order> list = null;
        if(null != st || "".equals(st)){
            list = orderService.findAllOrders();
        }else{
            list = orderService.findAllOrders(st);
        }
        request.setAttribute("allOrders",list);
        return "/admin/order/list.jsp";
    }
}
