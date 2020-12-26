package vn.com.r2s.fms.api.Dto;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import vn.com.r2s.fms.api.entity.Assignment;
import vn.com.r2s.fms.api.entity.Trainee;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TraineeAssignmentDto {
	

	public String getRegistrationCode() {
		return registrationCode;
	}

	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}

	public String getTraineeId() {
		return traineeId;
	}

	public void setTraineeId(String traineeId) {
		this.traineeId = traineeId;
	}

	@NonNull
	public String registrationCode;
	
	@NonNull
	public String traineeId;

	public TraineeAssignmentDto(String registrationCode, String traineeId) {
		super();
		this.registrationCode = registrationCode;
		this.traineeId = traineeId;
	}

	public TraineeAssignmentDto() {
		super();
	}
	
	

	
	
}