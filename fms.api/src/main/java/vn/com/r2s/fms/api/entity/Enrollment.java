package vn.com.r2s.fms.api.entity;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import vn.com.r2s.fms.api.audit.Audittable;

@Entity
@Table(name = "enrollments")
//@EntityListeners(AuditingEntityListener.class)
//public class Enrollment  extends Audittable<String>{
public class Enrollment {
	
	@EmbeddedId
	private EnrollmentID enrollmentID;
	
	@ManyToOne
	@MapsId(value = "classID")
	@JoinColumn(name = "ClassID")
	private Class ClassID;
	
	
	@ManyToOne
	@MapsId(value = "userName")
	@JoinColumn(name = "TraineeID")
	private Trainee traineeID;
	

//	@Column(name = "className")
//	private String className;
//
//	@Column(name = "traineeName")
//	private String traineeName;

	public Enrollment() {

	}

	public Enrollment(EnrollmentID enrollmentID) {
		this.enrollmentID = enrollmentID;
		
	}


	public Class getClassID() {
		return ClassID;
	}
	

	public void setClassID(Class classID) {
		ClassID = classID;
	}
	
	public EnrollmentID getEnrollmentID() {
		return enrollmentID;
	}


	public void setEnrollmentID(EnrollmentID enrollmentID) {
		this.enrollmentID = enrollmentID;
	}
	

	

	public Trainee getTraineeID() {
		return traineeID;
	}

	public void setTraineeID(Trainee traineeID) {
		this.traineeID = traineeID;
	}

//	public String getClassName() {
//		return className;
//	}
//
//	public void setClassName(String className) {
//		this.className = className;
//	}
//
//	public String getTraineeName() {
//		return traineeName;
//	}
//
//	public void setTraineeName(String traineeName) {
//		this.traineeName = traineeName;
//	}

	
	
	
	
	
}
