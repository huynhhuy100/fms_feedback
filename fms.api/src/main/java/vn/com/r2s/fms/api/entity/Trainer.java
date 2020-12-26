package vn.com.r2s.fms.api.entity;

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

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import vn.com.r2s.fms.api.audit.Audittable;

@Entity
@Table(name = "trainer")
@EntityListeners(AuditingEntityListener.class)
public class Trainer extends Audittable<String>{

	@Id
	@Column(name = "user_name", nullable = false)
	private String userName;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "email",nullable = false)
	private String email;
	
	@Column(name = "phone",nullable = false)
	private String phone;
	
	@Column(name = "address",nullable = false)
	private String address;
	
	@Column(name = "is_active",nullable = false)
	private boolean isActive;
	
	@Column(name = "idSkill",nullable = false)
	private int idSkill;
	
	@Column(name = "password",nullable = false)
	private String password;
	
	@Column(name = "activation_code",nullable = false)
	private String activationCode;
	
	@Column(name = "reset_password_code",nullable = false)
	private String resetPasswordCode;
	
	@Column(name = "isReceiveNotification",nullable = false)
	private boolean isReceiveNotification;

    @OneToMany(mappedBy = "assignmentKey.trainerId", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Assignment> assignment;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getIdSkill() {
		return idSkill;
	}

	public void setIdSkill(int idSkill) {
		this.idSkill = idSkill;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public String getResetPasswordCode() {
		return resetPasswordCode;
	}

	public void setResetPasswordCode(String resetPasswordCode) {
		this.resetPasswordCode = resetPasswordCode;
	}

	public boolean isReceiveNotification() {
		return isReceiveNotification;
	}

	public void setReceiveNotification(boolean isReceiveNotification) {
		this.isReceiveNotification = isReceiveNotification;
	}
	
}
