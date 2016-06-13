package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.Comment;
import bulletinBoard.beans.UsersComment;
import bulletinBoard.exception.SQLRuntimeException;

public class CommentDao {

	public void insert(Connection connection, Comment comment) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO comment ( ");
			sql.append("text");
			sql.append(",user_id");
			sql.append(",article_id");
			sql.append(",insert_date");
			sql.append(") VALUES (");
			sql.append("?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, comment.getText());
			ps.setInt(2, comment.getUserId());
			ps.setInt(3, comment.getArticleId());

//			System.out.println(message.getUserId());//★

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<UsersComment> getUsersComment(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM  users_comment ");
			sql.append("ORDER BY insert_date DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UsersComment> ret = toUsersCommentList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void delete(Connection connection, int articleId ) {

		PreparedStatement ps = null;
		try {
			String sql = "DELETE FROM comment WHERE article_id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1,articleId);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void deleteOnly(Connection connection, int commentId ) {

		PreparedStatement ps = null;
		try {
			String sql = "DELETE FROM comment WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1,commentId);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UsersComment> toUsersCommentList(ResultSet rs)
			throws SQLException {

		List<UsersComment> ret = new ArrayList<UsersComment>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String text = rs.getString("text");
				String name = rs.getString("name");
				int userId = rs.getInt("user_id");
				int articleId = rs.getInt("article_id");
				int branchId = rs.getInt("branch_id");
				int jobtitleId = rs.getInt("jobtitle_id");
				Timestamp insertDate = rs.getTimestamp("insert_date");

				UsersComment comment = new UsersComment();
				comment.setId(id);
				comment.setText(text);
				comment.setName(name);
				comment.setUserId(userId);
				comment.setArticleId(articleId);
				comment.setBranchId(branchId);
				comment.setJobTitleId(jobtitleId);
				comment.setInsertDate(insertDate);

				ret.add(comment);
			}
			return ret;
		} finally {
			close(rs);
		}
	}


}
