package test.bean;

import DB.DBAccess;
import bean.HelloWorld;
import bean.InterfaceA;
import bean.InterfaceImpl;
import bean.UserMessage;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args){
        ApplicationContext ac = new ClassPathXmlApplicationContext("Application-bean.xml");

        HelloWorld hw1 = (HelloWorld)ac.getBean("helloWorldImpl1");
        HelloWorld hw2 = (HelloWorld)ac.getBean("helloWorldImpl2");
        hw1.printHelloWorld();
        System.out.println();
        hw1.doPrint();

        System.out.println();
        hw2.doPrint();


        DBAccess dbAccess = new DBAccess();
        SqlSession sqlSession = null;
        List<UserMessage> list = new ArrayList<UserMessage>();
        try {
            sqlSession = dbAccess.getSQLSession();
            list = sqlSession.selectList("Message.queryUserMessage");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (sqlSession!=null)
            sqlSession.close();
        }

        for (UserMessage li:list){
            System.out.println("UserMessage:"+li.getMessage());
        }
    }
}
