package cn.store.dao;

import cn.store.domain.User;

import java.sql.SQLException;

public interface Userdao {

    void userRegist(User user)throws Exception;

    User userAction(String code) throws SQLException;

    void updateUser(User user) throws SQLException;

    User userLogin(User user) throws SQLException;
}
