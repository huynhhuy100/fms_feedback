package vn.com.r2s.fms.api.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import vn.com.r2s.fms.api.audit.Audittable;

@Entity
@Table(name = "Module")
@EntityListeners(AuditingEntityListener.class)
public class Module extends Audittable<String>{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int moduleId;
	
	@Column(name = "idAdmin", nullable = false)
	private String idAdmin;

	@Column(name = "moduleName", nullable = false)
	private String moduleName;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso=ISO.DATE)
	@Column(name = "startTime",nullable = false)
	private Date startTime;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso=ISO.DATE)
	@Column(name = "endTime",nullable = false)
	private Date endTime;
	
	@Column(name = "isDeleted",nullable = false)
	private boolean isDeleted;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name = "FeedbackStartTime")
	private LocalDateTime feedbackStartTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name = "FeedbackEndTime")
	private LocalDateTime feedbackEndTime;

	
	@Column(name = "idFeedback", nullable = false)
	private int idFeedback;
	
    @OneToMany(mappedBy = "assignmentKey.moduleId", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Assignment> assignment;
	


	

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public List<Assignment> getAssignment() {
		return assignment;
	}

	public void setAssignment(List<Assignment> assignment) {
		this.assignment = assignment;
	}

	public String getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(String idAdmin) {
		this.idAdmin = idAdmin;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	
	public LocalDateTime getFeedbackStartTime() {
		return feedbackStartTime;
	}

	public void setFeedbackStartTime(LocalDateTime feedbackStartTime) {
		this.feedbackStartTime = feedbackStartTime;
	}

	public LocalDateTime getFeedbackEndTime() {
		return feedbackEndTime;
	}

	public void setFeedbackEndTime(LocalDateTime feedbackEndTime) {
		this.feedbackEndTime = feedbackEndTime;
	}

	public int getIdFeedback() {
		return idFeedback;
	}

	public void setIdFeedback(int idFeedback) {
		this.idFeedback = idFeedback;
	}
	
	
	

}
