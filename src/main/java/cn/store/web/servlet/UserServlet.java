package cn.store.web.servlet;

import cn.store.domain.User;
import cn.store.service.UserService;
import cn.store.service.impl.UserServiceImpl;
import cn.store.utils.MailUtils;
import cn.store.utils.MyBeanUtils;
import cn.store.utils.UUIDUtils;
import cn.store.web.base.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class UserServlet extends BaseServlet {

    public String registUI(HttpServletRequest request, HttpServletResponse response){
        return "/jsp/register.jsp";
    }

    //注册
    public String UserRegist(HttpServletRequest request,HttpServletResponse response){
        Map<String,String[]> map = request.getParameterMap();
        User user = new User();
        user.setUid(UUIDUtils.getId());
        user.setState(0);
        user.setCode(UUIDUtils.getCode());
        MyBeanUtils.populate(user, map);
        System.out.println(user);

        UserService userService = new UserServiceImpl();

        try {
            userService.userRegister(user);
            System.out.println("注册成功，发送邮件");
            MailUtils.sendMail(user.getEmail(),user.getCode());
            request.setAttribute("msg","用户注册成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/jsp/info.jsp";
    }

    //用户激活
    public String active(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String code = request.getParameter("code");
        UserService userService = new UserServiceImpl();
        boolean flag = userService.userActive(code);
        if(flag == true){
            request.setAttribute("msg","用户激活成功，请登录");
            return "/jsp/login.jsp";
        }else {
            request.setAttribute("msg","用户激活失败，请重新注册");
            return "/jsp/info.jsp";
        }
    }

    //login
    public String userLogin(HttpServletRequest request,HttpServletResponse response)throws Exception{
        User user = new User();
        MyBeanUtils.populate(user,request.getParameterMap());
        UserService userService = new UserServiceImpl();
        User user2 = null;
        try {
            user2 = userService.userLogin(user);
            request.getSession().setAttribute("loginUser",user2);
            response.sendRedirect("/Store/index.jsp");
            return null;
        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            request.setAttribute("msg",msg);
            return "/jsp/login.jsp";
        }
    }

    //退出登录
    public String logOut(HttpServletRequest request,HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect("/Store/index.jsp");
        return null;
    }
}
