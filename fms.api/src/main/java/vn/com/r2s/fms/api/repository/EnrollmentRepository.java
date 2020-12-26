package vn.com.r2s.fms.api.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.r2s.fms.api.entity.Enrollment;
import vn.com.r2s.fms.api.entity.EnrollmentID;


public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentID>{
	List<Enrollment> findByEnrollmentIDTraineeId(String traineeId);
	
	List<Enrollment> findByEnrollmentIDClassId(Integer classId);

	Optional<Enrollment> findByEnrollmentIDClassIdAndEnrollmentIDTraineeId(Integer classID, String traineeId);

	
}
