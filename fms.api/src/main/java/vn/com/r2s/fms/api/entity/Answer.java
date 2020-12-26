package vn.com.r2s.fms.api.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "answer")
@EntityListeners(AuditingEntityListener.class)
public class Answer {
	
	@EmbeddedId
	private AnswerKey answerKey;
	
	@Column(name = "value")
	private String value;

	public AnswerKey answerKey() {
		return answerKey;
	}

	public AnswerKey getAnswerKey() {
		return answerKey;
	}


	public void setAnswerKey(AnswerKey answerKey) {
		this.answerKey = answerKey;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Answer() {
		super();
	}
	
}