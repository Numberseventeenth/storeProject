package cn.store.web.servlet;


import cn.store.domain.Cart;
import cn.store.domain.CartItem;
import cn.store.domain.Product;
import cn.store.service.ProductService;
import cn.store.service.impl.ProductServiceImpl;
import cn.store.web.base.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CartServlet")
public class CartServlet extends BaseServlet {

    public String addCartItemToCart(HttpServletResponse response, HttpServletRequest request)throws Exception{
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if(null == cart){
            cart = new Cart();
            request.getSession().setAttribute("cart",cart);
        }
        String pid = request.getParameter("pid");
        System.out.println(pid);
        int num = Integer.parseInt(request.getParameter("quantity"));
        ProductService productService = new ProductServiceImpl();
        Product product = productService.findProductByPid(pid);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);

        cart.addCartItemToCart(cartItem);

        response.sendRedirect("/Store/jsp/cart.jsp");
        return null;
    }

    public String removeCartItem(HttpServletRequest request,HttpServletResponse response)throws Exception{
        String pid = request.getParameter("id");
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        cart.removeCartItem(pid);
        response.sendRedirect("/Store/jsp/cart.jsp");
        return null;
    }

    public String clearCart(HttpServletRequest request,HttpServletResponse response)throws Exception{
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        cart.clearCart();
        response.sendRedirect("/Store/jsp/cart.jsp");
        return null;
    }
}
