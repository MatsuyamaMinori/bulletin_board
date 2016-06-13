package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtil.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulletinBoard.beans.Articles;
import bulletinBoard.beans.UsersArticles;
import bulletinBoard.dao.ArticlesDao;
import bulletinBoard.dao.UsersArticlesDao;

public class ArticlesService {

	public void register(Articles articles) {

		Connection connection = null;
		try {
			connection = getConnection();

			ArticlesDao articlesDao = new ArticlesDao();
			articlesDao.insert(connection, articles);

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

	public List<String> getCategory() {

		Connection connection = null;
		try {
			connection = getConnection();

			ArticlesDao articlesDao = new ArticlesDao();
			List<String> ret = articlesDao.getCategory(connection);

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

	private static final int LIMIT_NUM = 1000;

	public List<UsersArticles> getArticles() {

		Connection connection = null;
		try {
			connection = getConnection();

			UsersArticlesDao articlesDao = new UsersArticlesDao();
			List<UsersArticles> ret = articlesDao.getUsersArticles(connection, LIMIT_NUM);

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

	public List<UsersArticles> getCategoryArticles(String category,String beforeYear,String beforeManth,String beforeDate,
			String afterYear,String afterManth,String afterDate) {

		Connection connection = null;
		try {
			connection = getConnection();

			UsersArticlesDao articlesDao = new UsersArticlesDao();
			List<UsersArticles> ret = articlesDao.getCategoryArticles(connection, category,beforeYear,beforeManth,
					beforeDate,afterYear,afterManth,afterDate);

			commit(connection);
			if(ret == null){
				System.out.println("serviceでnull");
			}

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

			ArticlesDao articlesDao = new ArticlesDao();
			articlesDao.delete(connection, articleId);

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
