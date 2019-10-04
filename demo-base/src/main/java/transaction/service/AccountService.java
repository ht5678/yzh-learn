package transaction.service;

import java.util.List;

/**
 * 
 * 
 * 
 * @author yuezh2   2019年10月4日 下午11:26:07
 *
 */
public interface AccountService {

	public void addAccount(String name, int initMenoy);

    List<Account> queryAccount(String name);

    int updateAccount(String name, int money);
}

