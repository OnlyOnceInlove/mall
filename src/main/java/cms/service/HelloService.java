package cms.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HelloService {
    @Autowired
    SqlSession sqlSession;

    public List<Map> selVersion() {
        return sqlSession.selectList("planDao.select");
    }

}
