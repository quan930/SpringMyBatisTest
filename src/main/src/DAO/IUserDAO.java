package DAO;

import bean.UserMessage;

public interface IUserDAO {

    //注册
    public UserMessage Register(UserMessage user);

    //删除
    public int DeleteUser(UserMessage user);

}
