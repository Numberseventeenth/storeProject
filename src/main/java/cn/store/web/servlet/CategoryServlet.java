package cn.store.web.servlet;

import cn.store.domain.Category;
import cn.store.service.CategoryService;
import cn.store.service.impl.CategoryServiceImpl;
import cn.store.utils.JedisUtils;
import cn.store.web.base.BaseServlet;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/CategoryServlet")
public abstract class CategoryServlet extends BaseServlet {

    public String findAllCates(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        System.out.println("进入jedis");
        Jedis jedis = JedisUtils.getJedis();
        String jsonStr = jedis.get("allCats");
        if(null == jsonStr || "".equals(jsonStr)){
            CategoryService categoryService = new CategoryServiceImpl();
            List<Category> list = categoryService.getAllCates();
            jsonStr = JSONArray.fromObject(list).toString();
            System.out.println(jsonStr);
            jedis.set("allCats",jsonStr);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(jsonStr);
        }else{
            System.out.println("redis缓存中有数据");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(jsonStr);
        }

        JedisUtils.closeJedis(jedis);
        return null;
    }



}
