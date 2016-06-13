package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtil.*;
import static bulletinBoard.utils.DBUtil.*;

//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
import java.sql.Connection;
import java.util.List;

import bulletinBoard.beans.User;
import bulletinBoard.dao.UserDao;
import bulletinBoard.utils.CipherUtil;
//import twitter.utils.StreamUtil;

public class UserService {

	public void register(User user) {

		Connection connection = null;
		try {
			connection = getConnection();

			String encPassword = CipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);

			UserDao userDao = new UserDao();
			userDao.insert(connection, user);

			commit(connection);
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

	public List<User> getUserAll() {

		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			List<User> users = userDao.getUserAll(connection);

			commit(connection);

			return users;
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

	public void stoped(int userId) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			userDao.stoped(connection, userId);

			commit(connection);
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

	public void recovery(int userId) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			userDao.recovery(connection, userId);

			commit(connection);
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

	public void delete(int userId) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			userDao.delete(connection, userId);

			commit(connection);
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

	public void update(User user,boolean passUpdateFlg) {

		Connection connection = null;
		try {
			connection = getConnection();
			UserDao userDao = new UserDao();

			if(passUpdateFlg == true){
				String encPassword = CipherUtil.encrypt(user.getPassword());
				user.setPassword(encPassword);
				userDao.update(connection, user);
			} else {
				userDao.updateNoPass(connection, user);
			}

			commit(connection);
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

	public User getUser(int userId) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			User user = userDao.getUser(connection, userId);

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
