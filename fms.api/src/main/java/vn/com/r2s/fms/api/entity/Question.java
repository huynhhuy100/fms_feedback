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
@Table(name = "Question")
@EntityListeners(AuditingEntityListener.class)
public class Question extends Audittable<String>{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int questionID;

	@Column(name = "topicId", nullable = false)
	private int topicId;

	@Column(name = "QuestionContent",nullable = false)
	private String questionContent;

	@Column(name = "isDeleted",nullable = false)
	private boolean isdeleted;

	public int getQuestionID() {
		return questionID;
	}

	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	public int gettopicId() {
		return topicId;
	}

	public void settopicId(int topicId) {
		this.topicId = topicId;
	}

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	public boolean isIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}
}
