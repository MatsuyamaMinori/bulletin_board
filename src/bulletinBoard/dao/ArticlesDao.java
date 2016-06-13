package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.Articles;
import bulletinBoard.exception.SQLRuntimeException;

public class ArticlesDao {

	public void insert(Connection connection, Articles articles) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO articles ( ");
			sql.append("titel");
			sql.append(", text");
			sql.append(", category");
			sql.append(", user_id");
			sql.append(", insert_date");
			sql.append(") VALUES (");
			sql.append("?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, articles.getTitel());
			ps.setString(2, articles.getText());
			ps.setString(3, articles.getCategory());
			ps.setInt(4, articles.getUserId());

//			System.out.println(message.getUserId());//★

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void delete(Connection connection, int articleId ) {

		PreparedStatement ps = null;
		try {
			String sql = "DELETE FROM articles WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1,articleId);

//			System.out.println(message.getUserId());//★

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	public List<String> getCategory(Connection connection) {

		PreparedStatement ps = null;
		try{
			String sql = "SELECT category FROM articles";

			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<String> categoryList = toCategoryList(rs);
			if (categoryList.isEmpty() == true) {
				return null;
			} else {
				return categoryList;
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<String> toCategoryList(ResultSet rs) throws SQLException {

		List<String> ret = new ArrayList<String>();
		try {
			while (rs.next()) {
				String category = rs.getString("category");
				ret.add(category);
			}
			return ret;
		} finally {
			close(rs);
		}
	}
}
