package transaction.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * 
 * 
 * @author yuezh2   2019年10月4日 下午11:28:33
 *
 */
@Service
public class AccountServiceImpl implements AccountService {

	
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addAccount(String name, int initMoney) {
        String accountid = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        jdbcTemplate.update("insert INTO account (accountName,user,money) VALUES (?,?,?)", accountid, name, initMoney);
    }

    @Override
    @Transactional
    public List<Account> queryAccount(String name) {
        List<Account> list = jdbcTemplate.queryForList("SELECT * from account where user=?", Account.class, name);
        Arrays.toString(list.toArray());
        return list;
    }

    @Override
    @Transactional
    public int updateAccount(String name, int money) {
        return jdbcTemplate.update("SELECT * from account set money=money+? where user=?", money, name);
    }

}

