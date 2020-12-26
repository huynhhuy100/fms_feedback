package vn.com.r2s.fms.api.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class AssignmentKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "classId", referencedColumnName = "classID", insertable = false)
	private Class classId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "moduleId", referencedColumnName = "moduleId", insertable = false)
	private Module moduleId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trainerId", referencedColumnName = "user_name", insertable = false)
	private Trainer trainerId;

	public Class getClassId() {
		return classId;
	}

	public void setClassId(Class classId) {
		this.classId = classId;
	}

	public Module getModuleId() {
		return moduleId;
	}

	public void setModuleId(Module moduleId) {
		this.moduleId = moduleId;
	}

	public Trainer getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(Trainer trainerId) {
		this.trainerId = trainerId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classId == null) ? 0 : classId.hashCode());
		result = prime * result + ((moduleId == null) ? 0 : moduleId.hashCode());
		result = prime * result + ((trainerId == null) ? 0 : trainerId.hashCode());
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
		AssignmentKey other = (AssignmentKey) obj;
		if (classId == null) {
			if (other.classId != null)
				return false;
		} else if (!classId.equals(other.classId))
			return false;
		if (moduleId == null) {
			if (other.moduleId != null)
				return false;
		} else if (!moduleId.equals(other.moduleId))
			return false;
		if (trainerId == null) {
			if (other.trainerId != null)
				return false;
		} else if (!trainerId.equals(other.trainerId))
			return false;
		return true;
	}

	public AssignmentKey(Class classId, Module moduleId, Trainer trainerId) {
		super();
		this.classId = classId;
		this.moduleId = moduleId;
		this.trainerId = trainerId;
	}

	public AssignmentKey() {
		super();
	}

	public AssignmentKey(Trainer trainerId2) {
		// TODO Auto-generated constructor stub
	}

}
