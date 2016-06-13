package bulletinBoard.beans;

import java.io.Serializable;
import java.util.Date;

public class UsersArticles implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String titel;
	private String text;
	private String category;
	private String name;
	private int branchId;
	private int jobTitleId;
	private int userId;
	private Date insertDate;

	public int getJobTitleId() {
		return jobTitleId;
	}

	public void setJobTitleId(int jobTitleId) {
		this.jobTitleId = jobTitleId;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

}
