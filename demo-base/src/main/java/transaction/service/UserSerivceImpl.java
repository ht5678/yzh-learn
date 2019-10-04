package transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * 
 * 
 * @author yuezh2   2019年10月4日 下午11:28:51
 *
 */
@Service
public class UserSerivceImpl  implements UserSerivce {


    @Autowired
    AccountService accountService;
    @Autowired
    JdbcTemplate jdbcTemplate;



    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createUser(String name) {
        // 插入user 记录
        jdbcTemplate.update("INSERT INTO `user` (name) VALUES(?)", name);
        // 调用 accountService 添加帐户
        accountService.addAccount(name, 10000);
        // 人为报错
        int i = 1 / 0;
    }



}


