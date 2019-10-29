package cn.store.service;

import cn.store.domain.User;

import java.sql.SQLException;

public interface UserService {

    void userRegister(User user)throws Exception;

    boolean userActive(String code)throws Exception;

    User userLogin(User user) throws SQLException;
}
