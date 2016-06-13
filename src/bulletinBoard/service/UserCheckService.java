package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtil.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;

import bulletinBoard.beans.User;
import bulletinBoard.dao.UserDao;
import bulletinBoard.utils.CipherUtil;

public class UserCheckService {

	public User userCheckId(String loginId) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			User user = userDao.getCheckId(connection,loginId);

			commit(connection);

			return user;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public User userCheckPassword(String password) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			String encPassword = CipherUtil.encrypt(password);
			User user = userDao.getCheckPassword(connection,encPassword);

			commit(connection);

			return user;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

}
