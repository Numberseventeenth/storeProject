package cn.store.utils;





import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class MyBeanUtils {

    public static void populate(Object obj, Map<String,String[]> map){
        try {
            DateConverter dt = new DateConverter();
            dt.setPattern("yyyy-MM-dd");
            ConvertUtils.register(dt,java.util.Date.class);
            BeanUtils.populate(obj, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static<T> T populate(Class<T> clazz,Map<String,String[]> map){
        try {
            T obj = clazz.newInstance();
            DateConverter dt = new DateConverter();
            dt.setPattern("yyyy-MM-dd");
            ConvertUtils.register(dt,java.util.Date.class);
            BeanUtils.populate(obj,map);
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
