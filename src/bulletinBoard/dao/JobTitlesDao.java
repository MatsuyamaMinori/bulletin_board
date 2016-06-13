package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.JobTitles;
import bulletinBoard.exception.SQLRuntimeException;

public class JobTitlesDao {
	public List<JobTitles> getJobTitles(Connection connection/*, int num*/) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT id,name FROM  JobTitles");
//			sql.append("ORDER BY insert_date DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<JobTitles> ret = toJobTitlesList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<JobTitles> toJobTitlesList(ResultSet rs)
			throws SQLException {

		List<JobTitles> ret = new ArrayList<JobTitles>();
		try {
			while (rs.next()) {
				int JobTitleId = rs.getInt("id");
				String JobTitleName = rs.getString("name");
//				Timestamp insertDate = rs.getTimestamp("insert_date");

				JobTitles JobTitle = new JobTitles();

				JobTitle.setJobTitleId(JobTitleId);
				JobTitle.setJobTitleName(JobTitleName);
//				message.setInsertDate(insertDate);

				ret.add(JobTitle);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
