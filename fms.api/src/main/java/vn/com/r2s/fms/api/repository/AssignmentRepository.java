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
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, AssignmentKey> {

	Optional<Assignment> findById(AssignmentKey id);
	
	List<Assignment> findByAssignmentKeyTrainerId(Trainer trainerId);

	List<Assignment> findByAssignmentKeyClassId(Class classId);

	List<Assignment> findByAssignmentKeyModuleId(Module moduleId);
	
	List<Assignment> findByRegistrationCode(String registrationCode);
}