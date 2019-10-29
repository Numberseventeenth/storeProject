package cn.store.utils;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import cn.store.dao.Userdao;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.store.domain.User;

public class BeanFactory {
	public static Object createObject(String name)  {
		try {
			SAXReader reader = new SAXReader();
			InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("application.xml");
			Document doc = reader.read(is);
			Element rootElement = doc.getRootElement();
			List<Element> list = rootElement.elements();
			for (Element element : list) {
				String id = element.attributeValue("id");
				if(id.equals(name)) {
					String str = element.attributeValue("class");
					Class clazz = Class.forName(str);
					return clazz.newInstance();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) throws SQLException {
		/*Userdao userDao = (UserDao) BeanFactory.createObject("userDao");
		User user = new User();
		user.setUsername("aaa");
		user.setPassword("123");
		User user2 = userDao.userLogin(user);
		System.out.println(user2);*/
	}
}
