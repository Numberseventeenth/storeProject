package cn.store.web.servlet;


import cn.store.domain.PageModel;
import cn.store.domain.Product;
import cn.store.service.ProductService;
import cn.store.service.impl.ProductServiceImpl;
import cn.store.web.base.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet("/productServlet")
public class ProductServlet extends BaseServlet {

    public String findProductByPid(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String pid = request.getParameter("pid");
        ProductService productService = new ProductServiceImpl();
        Product product = productService.findProductByPid(pid);
        request.setAttribute("product",product);
        return "/jsp/product_info.jsp";
    }

    public String findProductByCidWithPage(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        String cid = request.getParameter("cid");
        int curNum = Integer.parseInt(request.getParameter("num"));
        ProductService productService = new ProductServiceImpl();
        PageModel pm = productService.findProductByCidWithPage(cid,curNum);
        request.setAttribute("page",pm);
        System.out.println(pm.getUrl());
        return "/jsp/product_list.jsp";
    }
}
