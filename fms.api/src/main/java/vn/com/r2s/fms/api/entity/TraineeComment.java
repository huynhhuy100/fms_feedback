package vn.com.r2s.fms.api.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "traineeComment")
@EntityListeners(AuditingEntityListener.class)
public class TraineeComment {
	
	@EmbeddedId
	private TraineeCommentKey traineeCommentKey;
	
	@Column(name = "comment")
	private String comment;

	public TraineeCommentKey traineeCommetKey() {
		return traineeCommentKey;
	}

	public TraineeCommentKey getTraineeCommentKey() {
		return traineeCommentKey;
	}

	public void setTraineeCommentKey(TraineeCommentKey traineeCommentKey) {
		this.traineeCommentKey = traineeCommentKey;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	 public TraineeComment() {
		super();
	}
	
}