package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtil.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulletinBoard.beans.Comment;
import bulletinBoard.beans.UsersComment;
import bulletinBoard.dao.CommentDao;

public class CommentService {

	public void register(Comment comment) {

		Connection connection = null;
		try {
			connection = getConnection();

			CommentDao commentDao = new CommentDao();
			commentDao.insert(connection, comment);

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


	private static final int LIMIT_NUM = 1000;

	public List<UsersComment> getComment() {

		Connection connection = null;
		try {
			connection = getConnection();

			CommentDao commentDao = new CommentDao();
			List<UsersComment> ret = commentDao.getUsersComment(connection, LIMIT_NUM);

			commit(connection);

			return ret;
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

	public void delete(int articleId) {

		Connection connection = null;
		try {
			connection = getConnection();

			CommentDao commentDao = new CommentDao();
			commentDao.delete(connection, articleId);

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

	public void deleteOnly(int commentId) {

		Connection connection = null;
		try {
			connection = getConnection();

			CommentDao commentDao = new CommentDao();
			commentDao.deleteOnly(connection, commentId);

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

}
