package vn.com.r2s.fms.api.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

import vn.com.r2s.fms.api.entity.Enrollment;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import vn.com.r2s.fms.api.entity.Assignment;


import vn.com.r2s.fms.api.audit.Audittable;

@Entity
@Table(name = "class")
@EntityListeners(AuditingEntityListener.class)
public class Class extends Audittable<String>{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int classID;
	
	@Column(name = "classname", nullable = false)
	private String className;
	
	@Column(name = "capadity", nullable = false)
	private int capadity;
	
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
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "ClassID")
	Set<Enrollment> enrollments;
	
	@OneToMany(mappedBy = "assignmentKey.classId", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Assignment> assignment;
	
	public Class() {
		
	}
	
	public Class(String className, int capadity,Date startTime, Date endTime, boolean isDeleted) {
		super();
		this.className = className;
		this.capadity = capadity;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isDeleted = isDeleted;
		
	}

	public int getClassID() {
		return classID;
	}

	public void setClassID(int classID) {
		this.classID = classID;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getCapadity() {
		return capadity;
	}

	public void setCapadity(int capadity) {
		this.capadity = capadity;
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

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Set<Enrollment> getEnrollments() {
		return enrollments;
	}

	public void setEnrollments(Set<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}

}
