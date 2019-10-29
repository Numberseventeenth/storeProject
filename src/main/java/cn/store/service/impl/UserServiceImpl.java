package cn.store.service.impl;

import cn.store.dao.Userdao;
import cn.store.domain.User;
import cn.store.service.UserService;
import cn.store.utils.BeanFactory;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    Userdao userdao = (Userdao) BeanFactory.createObject("userDao");

    @Override
    public void userRegister(User user) throws Exception {
        userdao.userRegist(user);
    }

    @Override
    public boolean userActive(String code) throws Exception {
        User user = userdao.userAction(code);
        if(null != user){
            user.setState(1);
            user.setCode(null);
            userdao.updateUser(user);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public User userLogin(User user) throws SQLException {
        User uu = userdao.userLogin(user);
        if(null == uu){
            throw new RuntimeException("密码有误");
        }else if(uu.getState() == 0){
            throw new RuntimeException("用户未激活");
        }else{
            return uu;
        }
    }
}
