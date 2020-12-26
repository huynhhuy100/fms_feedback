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
@Table(name = "assignment")
@EntityListeners(AuditingEntityListener.class)
public class Assignment {
	
	@EmbeddedId
	private AssignmentKey assignmentKey;
	
	@Column(name = "registrationCode")
	private String registrationCode;

	public AssignmentKey getAssignmentKey() {
		return assignmentKey;
	}

	public void setAssignmentKey(AssignmentKey assignmentKey) {
		this.assignmentKey = assignmentKey;
	}


	public String getRegistrationCode() {
		return registrationCode;
	}


	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}


	public Assignment() {
		super();
	}
	
}