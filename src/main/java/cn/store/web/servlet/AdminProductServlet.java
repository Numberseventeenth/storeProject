package cn.store.web.servlet;

import cn.store.domain.Category;
import cn.store.domain.PageModel;
import cn.store.domain.Product;
import cn.store.service.CategoryService;
import cn.store.service.ProductService;
import cn.store.service.impl.CategoryServiceImpl;
import cn.store.service.impl.ProductServiceImpl;
import cn.store.utils.UUIDUtils;
import cn.store.utils.UploadUtils;
import cn.store.web.base.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/AdminProductServlet")
public class AdminProductServlet extends BaseServlet {

    public String findAllProductWithPage(HttpServletRequest request, HttpServletResponse response)throws Exception{
        int curNum = Integer.parseInt(request.getParameter("num"));
        ProductService productService = new ProductServiceImpl();
        PageModel pm = productService.findAllProductWithPage(curNum);
        request.setAttribute("page",pm);
        return "/admin/product/list.jsp";
    }

    public String addProjectUI(HttpServletRequest request,HttpServletResponse response)throws Exception{
        ProductService productService = new ProductServiceImpl();
        CategoryService categoryService = new CategoryServiceImpl();
        List<Category> allCates = categoryService.getAllCates();
        request.setAttribute("allCats",allCates);
        return "/admin/product/add.jsp";
    }

    public String addProduct(HttpServletRequest request,HttpServletResponse response)throws Exception{
        Map<String,String> map = new HashMap<>();
        Product product = new Product();
        try {
            DiskFileItemFactory fac = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(fac);
            List<FileItem> list = upload.parseRequest(request);
            for (FileItem item : list) {
                if(item.isFormField()){
                    map.put(item.getFieldName(),item.getString("utf-8"));
                }else{
                    String oldFileName = item.getName();
                    String newFileName = UploadUtils.getUUIDName(oldFileName);
                    InputStream is = item.getInputStream();
                    String realPath = getServletContext().getRealPath("/products/3/");
                    String dir = UploadUtils.getDir(newFileName);
                    String path = realPath = dir;
                    File newDir = new File(path);
                    if(!newDir.exists()){
                        newDir.mkdir();
                    }
                    File finalFile = new File(newDir,newFileName);
                    if(!finalFile.exists()){
                        finalFile.createNewFile();
                    }
                    OutputStream os = new FileOutputStream(finalFile);
                    IOUtils.copy(is,os);
                    IOUtils.closeQuietly(is);
                    IOUtils.closeQuietly(os);

                    map.put("pimage","/product/3/"+dir+"/"+newFileName);
                }
            }
            BeanUtils.populate(product,map);
            product.setPid(UUIDUtils.getId());
            product.setPdate(new Date());
            product.setPflag(0);

            ProductService productService = new ProductServiceImpl();
            productService.saveProduct(product);
            response.sendRedirect("/Stroe/AdminProductServlet?method=findAllProductWithPage&num=1");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
