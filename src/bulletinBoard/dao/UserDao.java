package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.User;
import bulletinBoard.exception.NoRowsUpdatedRuntimeException;
import bulletinBoard.exception.SQLRuntimeException;

public class UserDao {

	public User getUser(Connection connection, String loginId,String password) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT users.name,login_id,password,branch_id,jobtitle_id,stoped,"
					+ "users.id AS id,branches.name AS branchesName,jobtitles.name AS jobtitlesName "
					+ "FROM users INNER JOIN branches ON users.branch_id = branches.id "
					+ "INNER JOIN jobtitles ON users.jobtitle_id = jobtitles.id WHERE login_id = ? AND password = ?";

			ps = connection.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public User getCheckId(Connection connection,String inputloginId) {

		PreparedStatement ps = null;
		try{
			String sql = "SELECT users.name,login_id,password,branch_id,jobtitle_id,stoped,"
					+ "users.id AS id,branches.name AS branchesName,jobtitles.name AS jobtitlesName "
					+ "FROM users INNER JOIN branches ON users.branch_id = branches.id "
					+ "INNER JOIN jobtitles ON users.jobtitle_id = jobtitles.id WHERE login_id = ?";

			ps = connection.prepareStatement(sql);
			ps.setString(1, inputloginId);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public User getCheckPassword(Connection connection,String inputpassword) {

		PreparedStatement ps = null;
		try{
			String sql = "SELECT users.name,login_id,password,branch_id,jobtitle_id,stoped,"
					+ "users.id AS id,branches.name AS branchesName,jobtitles.name AS jobtitlesName "
					+ "FROM users INNER JOIN branches ON users.branch_id = branches.id "
					+ "INNER JOIN jobtitles ON users.jobtitle_id = jobtitles.id WHERE password = ?";

			ps = connection.prepareStatement(sql);
			ps.setString(1, inputpassword);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<User> getUserAll(Connection connection) {

		PreparedStatement ps = null;
		try{
			String sql = "SELECT users.name,login_id,password,branch_id,jobtitle_id,stoped,"
					+ "users.id AS id,branches.name AS branchesName,jobtitles.name AS jobtitlesName "
					+ "FROM users INNER JOIN branches ON users.branch_id = branches.id "
					+ "INNER JOIN jobtitles ON users.jobtitle_id = jobtitles.id";

			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else {
				return userList;
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<User> toUserList(ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<User>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String loginId = rs.getString("login_id");
				String name = rs.getString("name");
				int branchId = rs.getInt("branch_id");
				String password = rs.getString("password");
				int jobTitleId = rs.getInt("jobtitle_id");
				int stoped = rs.getInt("stoped");
				String branchesName = rs.getString("branchesName");
				String jobtitlesName = rs.getString("jobtitlesName");
////				Timestamp insertDate = rs.getTimestamp("insert_date");
////				Timestamp updateDate = rs.getTimestamp("update_date");

				User user = new User();
				user.setId(id);
				user.setLoginId(loginId);
				user.setName(name);
				user.setBranchId(branchId);
				user.setPassword(password);
				user.setJobTitleId(jobTitleId);
				user.setStoped(stoped);
				user.setBranchName(branchesName);
				user.setJobTitleName(jobtitlesName);
////				user.setInsertDate(insertDate);
////				user.setUpdateDate(updateDate);

				ret.add(user);
			}
			return ret;
		} finally {
			close(rs);
		}
	}


	public void insert(Connection connection, User user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO users ( ");
			sql.append("login_id");
			sql.append(", password");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(", jobtitle_id");
			sql.append(", stoped");
//			sql.append(", insert_date");
//			sql.append(", update_date");
			sql.append(") VALUES (");
			sql.append("?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", 0");
//			sql.append(", CURRENT_TIMESTAMP"); // insert_date
//			sql.append(", CURRENT_TIMESTAMP"); // update_date
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setInt(4, user.getBranchId());
			ps.setInt(5, user.getJobTitleId());

			//★sql check
			System.out.println(ps.toString());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void stoped(Connection connection, int userId) {

		PreparedStatement ps = null;
		try {
			String sql ="UPDATE users SET stoped = 1 WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void recovery(Connection connection, int userId) {

		PreparedStatement ps = null;
		try {
			String sql ="UPDATE users SET stoped = 0 WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void delete(Connection connection, int userId ) {

		PreparedStatement ps = null;
		try {
			String sql = "DELETE FROM users WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1,userId);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	public void update(Connection connection, User user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append("  login_id = ?");
			sql.append(", name = ?");
			sql.append(", branch_id = ?");
			sql.append(", password = ?");
			sql.append(", jobtitle_id = ?");
			sql.append(" WHERE");
			sql.append(" id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getName());
			ps.setInt(3, user.getBranchId());
			ps.setString(4, user.getPassword());
			ps.setInt(5, user.getJobTitleId());
			ps.setInt(6, user.getId());

			int count = ps.executeUpdate();
			if (count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	public void updateNoPass(Connection connection, User user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append("  login_id = ?");
			sql.append(", name = ?");
			sql.append(", branch_id = ?");
			sql.append(", jobtitle_id = ?");
			sql.append(" WHERE");
			sql.append(" id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getName());
			ps.setInt(3, user.getBranchId());
			ps.setInt(4, user.getJobTitleId());
			ps.setInt(5, user.getId());

			int count = ps.executeUpdate();
			if (count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	public User getUser(Connection connection, int id) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT users.name,login_id,password,branch_id,jobtitle_id,stoped,"
					+ "users.id AS id,branches.name AS branchesName,jobtitles.name AS jobtitlesName "
					+ "FROM users INNER JOIN branches ON users.branch_id = branches.id "
					+ "INNER JOIN jobtitles ON users.jobtitle_id = jobtitles.id WHERE users.id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

}

