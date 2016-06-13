package bulletinBoard.beans;

import java.io.Serializable;

public class JobTitles implements Serializable{
		private static final long serialVersionUID = 1L;

		private int jobTitleId;
		private String jobTitleName;

		public int getJobTitleId() {
			return jobTitleId;
		}

		public void setJobTitleId(int jobTitleId) {
			this.jobTitleId = jobTitleId;
		}

		public String getJobTitleName() {
			return jobTitleName;
		}

		public void setJobTitleName(String jobTitleName) {
			this.jobTitleName = jobTitleName;
		}

}
