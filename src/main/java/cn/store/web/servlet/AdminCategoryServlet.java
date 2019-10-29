package cn.store.web.servlet;

import cn.store.domain.Category;
import cn.store.domain.Order;
import cn.store.service.CategoryService;
import cn.store.service.OrderService;
import cn.store.service.impl.CategoryServiceImpl;
import cn.store.service.impl.OrderServiceImpl;
import cn.store.utils.UUIDUtils;
import cn.store.web.base.BaseServlet;
import net.sf.json.JSONArray;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {

    public String findAllCats(HttpServletRequest request, HttpServletResponse response)throws Exception{
        CategoryService categoryService = new CategoryServiceImpl();
        List<Category> list = categoryService.getAllCates();
        request.setAttribute("allCates",list);
        return "/admin/category/list.jsp";
    }

    public String addCategoryUI(HttpServletRequest request,HttpServletResponse response)throws Exception{
        return "/admin/category/add.jsp";
    }

    public String addCategory(HttpServletRequest request,HttpServletResponse response)throws Exception{
        String cname = request.getParameter("cname");
        String id = UUIDUtils.getId();
        Category category = new Category();
        category.setCid(id);
        category.setCname(cname);
        CategoryService categoryService = new CategoryServiceImpl();
        categoryService.addCategory(category);
        response.sendRedirect("/Store/AdminCategoryServlet?method=findAllCats");
        return null;
    }

    public String findOrderByOidWithAjax(HttpServletRequest request,HttpServletResponse response)throws Exception{
        String oid = request.getParameter("id");
        OrderService orderService = new OrderServiceImpl();
        Order order = orderService.findOrderByOid(oid);
        String jsonStr = JSONArray.fromObject(order.getList()).toString();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(jsonStr);
        return null;
    }

    public String updateOrderByOid(HttpServletRequest request,HttpServletResponse response)throws Exception{
        String oid = request.getParameter("oid");
        OrderService orderService = new OrderServiceImpl();
        Order order = orderService.findOrderByOid(oid);
        order.setState(3);
        orderService.updataOrder(order);
        response.sendRedirect("/Store/AdminOrderServlet?method=findOrders&state=3");
        return null;
    }
}
