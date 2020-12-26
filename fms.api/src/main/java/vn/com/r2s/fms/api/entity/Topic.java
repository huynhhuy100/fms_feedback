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
@Table(name = "topics")
@EntityListeners(AuditingEntityListener.class)
public class Topic extends Audittable<String>{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int topicId;

	@Column(name = "topic_name",nullable = false)
	private String topicName;

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}


}
