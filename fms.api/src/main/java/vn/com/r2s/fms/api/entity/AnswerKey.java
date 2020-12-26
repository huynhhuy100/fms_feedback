package vn.com.r2s.fms.api.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class AnswerKey implements Serializable{

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
	@JoinColumn(name = "traineeId", referencedColumnName = "user_name", insertable = false)
	private Trainee traineeId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "questionID", referencedColumnName = "questionID", insertable = false)
	private Question questionID;
	
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

	public Trainee getTraineeId() {
		return traineeId;
	}

	public void setTraineeId(Trainee traineeId) {
		this.traineeId = traineeId;
	}

	public Question getQuestionID() {
		return questionID;
	}

	public void setQuestionID(Question questionID) {
		this.questionID = questionID;
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
		result = prime * result + ((traineeId == null) ? 0 : traineeId.hashCode());
		result = prime * result + ((questionID == null) ? 0 : questionID.hashCode());
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
		AnswerKey other = (AnswerKey) obj;
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
		if (traineeId == null) {
			if (other.traineeId != null)
				return false;
		} else if (!traineeId.equals(other.traineeId))
			return false;
		if (questionID == null) {
			if (other.questionID != null)
				return false;
		} else if (!questionID.equals(other.questionID))
			return false;
		return true;
	}

	public AnswerKey(Class classId, Module moduleId, Trainee traineeId, Question questionId) {
		super();
		this.classId = classId;
		this.moduleId = moduleId;
		this.traineeId = traineeId;
		this.questionID = questionId;
	}

	public AnswerKey() {
		super();
	}

	public AnswerKey(Trainee traineeId2) {
		// TODO Auto-generated constructor stub
	}

}
