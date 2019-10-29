package cn.store.web.servlet;

import cn.store.domain.Product;
import cn.store.service.ProductService;
import cn.store.service.impl.ProductServiceImpl;
import cn.store.web.base.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/IndexServlet")
public class IndexServlet extends BaseServlet {

    public String execute(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        ProductService productService = new ProductServiceImpl();
        List<Product> list01 = productService.findHots();
        List<Product> list02 = productService.findNews();

        request.setAttribute("hots",list01);
        request.setAttribute("news",list02);

        return "/jsp/index.jsp";
    }
}
