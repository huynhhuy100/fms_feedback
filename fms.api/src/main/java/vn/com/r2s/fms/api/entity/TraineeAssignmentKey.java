package vn.com.r2s.fms.api.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class TraineeAssignmentKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "registrationCode", referencedColumnName = "registrationCode", insertable = false)
	private Assignment registrationCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "traineeId", referencedColumnName = "user_name", insertable = false)
	private Trainee traineeId;

	public Assignment getRegistrationCode() {
		return registrationCode;
	}

	public void setRegistrationCode(Assignment registrationCode) {
		this.registrationCode = registrationCode;
	}

	public Trainee getTraineeId() {
		return traineeId;
	}

	public void setTraineeId(Trainee traineeId) {
		this.traineeId = traineeId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((registrationCode == null) ? 0 : registrationCode.hashCode());
		result = prime * result + ((traineeId == null) ? 0 : traineeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TraineeAssignmentKey other = (TraineeAssignmentKey) obj;
		if (registrationCode == null) {
			if (other.registrationCode != null)
				return false;
		} else if (!traineeId.equals(other.traineeId))
			return false;
		return true;
	}

	public TraineeAssignmentKey(Assignment registrationCode, Trainee traineeId) {
		super();

		this.registrationCode = registrationCode;
		this.traineeId = traineeId;
	}

	public TraineeAssignmentKey() {
		super();
	}

	public TraineeAssignmentKey(Trainee traineeId2) {
		// TODO Auto-generated constructor stub
	}

}
