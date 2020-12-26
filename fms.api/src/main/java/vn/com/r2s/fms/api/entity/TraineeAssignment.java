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
@Table(name = "traineeAssignment")
@EntityListeners(AuditingEntityListener.class)
public class TraineeAssignment {
	
	@EmbeddedId
	private TraineeAssignmentKey traineeAssignmentKey;
	

	public TraineeAssignment(TraineeAssignmentKey traineeAssignmentKey) {
		super();
		this.traineeAssignmentKey = traineeAssignmentKey;
	}

	public TraineeAssignmentKey getTraineeAssignmentKey() {
		return traineeAssignmentKey;
	}

	public void setTraineeAssignmentKey(TraineeAssignmentKey traineeAssignmentKey) {
		this.traineeAssignmentKey = traineeAssignmentKey;
	}

	public TraineeAssignment() {
		super();
	}

}