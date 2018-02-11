package DB;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class DBAccess {

    public SqlSession getSQLSession() throws IOException {

        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");

        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(reader);

        SqlSession sqlSession = build.openSession();

        return sqlSession;
    }

}
