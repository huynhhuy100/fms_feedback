package vn.com.r2s.fms.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.com.r2s.fms.api.entity.Assignment;
import vn.com.r2s.fms.api.entity.AssignmentKey;
import vn.com.r2s.fms.api.entity.Trainer;
import vn.com.r2s.fms.api.entity.Class;
import vn.com.r2s.fms.api.entity.Module;
import vn.com.r2s.fms.api.entity.Trainee;
import vn.com.r2s.fms.api.entity.TraineeAssignment;
import vn.com.r2s.fms.api.entity.TraineeAssignmentKey;
@Repository
public interface TraineeAssignmentRepository extends JpaRepository<TraineeAssignment, TraineeAssignmentKey> {

	Optional<TraineeAssignment> findById(TraineeAssignmentKey id);
	
	List<TraineeAssignment> findByTraineeAssignmentKeyTraineeId(Trainee  traineeId);

	List<TraineeAssignment> findByTraineeAssignmentKeyRegistrationCode(Assignment registrationCode);

	
}