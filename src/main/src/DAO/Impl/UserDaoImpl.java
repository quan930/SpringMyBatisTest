package DAO.Impl;

import DAO.IUserDAO;
import DB.DBAccess;
import bean.UserMessage;

public class UserDaoImpl implements IUserDAO {

    private DBAccess dbAccess;

    public void setDbAccess(DBAccess dbAccess) {
        this.dbAccess = dbAccess;

    }

    public UserMessage Register(UserMessage user) {
        return null;
    }

    public int DeleteUser(UserMessage user) {
        return 0;
    }
}
