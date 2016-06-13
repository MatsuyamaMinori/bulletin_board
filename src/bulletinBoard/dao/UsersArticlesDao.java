package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.UsersArticles;
import bulletinBoard.exception.SQLRuntimeException;

public class UsersArticlesDao {

	public List<UsersArticles> getUsersArticles(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM  users_articles ");
			sql.append("ORDER BY insert_date DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UsersArticles> ret = toUsersArticlesList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<UsersArticles> getCategoryArticles(Connection connection, String category,
			String beforeYear,String beforeManth,String beforeDate,
			String afterYear,String afterManth,String afterDate) {

		PreparedStatement ps = null;
		String sql = null;
		try {
			if(category == null){

				sql = "SELECT * FROM  users_articles WHERE insert_date BETWEEN '"
						+ beforeYear + "-" + beforeManth + "-" + beforeDate +
						" 00:00:00' AND '" + afterYear + "-" + afterManth + "-" + afterDate + " 23:59:59'";
				System.out.println("daosqlnull");

			} else if(category.equals("all")){
					sql = "SELECT * FROM  users_articles WHERE insert_date BETWEEN '"
							+ beforeYear + "-" + beforeManth + "-" + beforeDate +
							" 00:00:00' AND '" + afterYear + "-" + afterManth + "-" + afterDate + " 23:59:59'";
					System.out.println("daosqlall");

			} else {
					sql = "SELECT * FROM  users_articles WHERE category = '" + category +
						"' AND insert_date BETWEEN '" + beforeYear + "-" + beforeManth + "-" + beforeDate +
						" 00:00:00' AND '" + afterYear + "-" + afterManth + "-" + afterDate + " 23:59:59'";
					System.out.println("daosql");
			}

			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<UsersArticles> ret = toUsersArticlesList(rs);
			if (ret.isEmpty() == true) {
				System.out.println("dao");
				return null;
			} else {
				return ret;
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UsersArticles> toUsersArticlesList(ResultSet rs)
			throws SQLException {

		List<UsersArticles> ret = new ArrayList<UsersArticles>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String titel = rs.getString("titel");
				String text = rs.getString("text");
				String category = rs.getString("category");
				String name = rs.getString("name");
				int branchId = rs.getInt("branch_id");
				int jobTitleId = rs.getInt("jobTitle_id");
				int userId = rs.getInt("user_id");

				Timestamp insertDate = rs.getTimestamp("insert_date");

				UsersArticles articles = new UsersArticles();
				articles.setId(id);
				articles.setTitel(titel);
				articles.setText(text);
				articles.setCategory(category);
				articles.setName(name);
				articles.setBranchId(branchId);
				articles.setJobTitleId(jobTitleId);
				articles.setUserId(userId);
				articles.setInsertDate(insertDate);

				ret.add(articles);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}