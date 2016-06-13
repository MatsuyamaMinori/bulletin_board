package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtil.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulletinBoard.beans.Branches;
import bulletinBoard.dao.BranchesDao;

public class BranchesService {
//	private static final int LIMIT_NUM = 1000;

	public List<Branches> getBranches() {

		Connection connection = null;
		try {
			connection = getConnection();

			BranchesDao branchesDao = new BranchesDao();
			List<Branches> ret = branchesDao.getBranches(connection/*, LIMIT_NUM*/);

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
