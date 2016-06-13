package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtil.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulletinBoard.beans.JobTitles;
import bulletinBoard.dao.JobTitlesDao;

public class JobTitlesService {
//	private static final int LIMIT_NUM = 1000;

	public List<JobTitles> getJobTitles() {

		Connection connection = null;
		try {
			connection = getConnection();

			JobTitlesDao jobTitlesDao = new JobTitlesDao();
			List<JobTitles> ret = jobTitlesDao.getJobTitles(connection/*, LIMIT_NUM*/);

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
}
