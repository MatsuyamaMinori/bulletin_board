package bulletinBoard.comparator;

import java.util.Comparator;

import bulletinBoard.beans.User;

public class ManagementUsersCom implements Comparator<User>{

	public int compare(User a,User b){
		int jobTitleId1 = a.getJobTitleId();
		int jobTitleId2 = b.getJobTitleId();
		int branchId1 = a.getBranchId();
		int branchId2 = b.getBranchId();
		int id1 = a.getId();
		int id2 = b.getId();

		if (branchId1 > branchId2) {
			return 1;

		} else if (branchId1 == branchId2) {
			if(jobTitleId1 > jobTitleId2){
				return 1;
			} else if(jobTitleId1 == jobTitleId2){
				if(id1 > id2){
					return 1;
				} else if(id1 == id2){
					return 0;
				} else {
					return -1;
				}
			} else {
				return -1;
			}

		} else {
			return -1;

		}
	}
}
