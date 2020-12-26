package vn.com.r2s.fms.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import vn.com.r2s.fms.api.audit.Audittable;

@Entity
@Table(name = "feedback")
@EntityListeners(AuditingEntityListener.class)
public class Feedback extends Audittable<String>{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int feedbackID;
	
	@Column(name = "typeFeedbackID",nullable = false)
	private int typeFeedbackID;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "adminID", nullable = false)
	private String adminID;
	
	@Column(name = "isDeleted",nullable = false)
	private boolean isDeleted;
	
	

	public int getFeedbackID() {
		return feedbackID;
	}

	public void setFeedbackID(int feedbackID) {
		this.feedbackID = feedbackID;
	}
	
	public int gettypeFeedbackID() {
		return typeFeedbackID;
	}

	public void settypeFeedbackID(int TypeFeedbackID) {
		typeFeedbackID = TypeFeedbackID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAdminID() {
		return adminID;
	}

	public void setAdminID(String adminID) {
		this.adminID = adminID;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setisDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	

	
}
