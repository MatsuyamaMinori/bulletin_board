package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.Branches;
import bulletinBoard.exception.SQLRuntimeException;

public class BranchesDao {
	public List<Branches> getBranches(Connection connection/*, int num*/) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT id,name FROM  branches");
//			sql.append("ORDER BY insert_date DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<Branches> ret = toBranchesList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Branches> toBranchesList(ResultSet rs)
			throws SQLException {

		List<Branches> ret = new ArrayList<Branches>();
		try {
			while (rs.next()) {
				int branchId = rs.getInt("id");
				String branchName = rs.getString("name");
//				Timestamp insertDate = rs.getTimestamp("insert_date");

				Branches branch = new Branches();

				branch.setBranchId(branchId);
				branch.setBranchName(branchName);
//				message.setInsertDate(insertDate);

				ret.add(branch);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
