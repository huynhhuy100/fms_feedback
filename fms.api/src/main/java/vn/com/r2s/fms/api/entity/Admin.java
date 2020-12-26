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
@Table(name = "admin")	
@EntityListeners(AuditingEntityListener.class)
public class Admin extends Audittable<String>{

	@Id
	@Column(name = "user_name", nullable = false)
	private String UserName;
	
	@Column(name = "name", nullable = false)
	private String Name;
	
	@Column(name = "email",nullable = false)
	private String Email;
	
	@Column(name = "password",nullable = false)
	private String Password;

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}
	
}
