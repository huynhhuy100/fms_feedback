package vn.com.r2s.fms.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.com.r2s.fms.api.entity.Class;
import vn.com.r2s.fms.api.entity.Module;
import vn.com.r2s.fms.api.entity.Trainee;
import vn.com.r2s.fms.api.entity.TraineeComment;
import vn.com.r2s.fms.api.entity.TraineeCommentKey;
@Repository
public interface TraineeCommentRepository extends JpaRepository<TraineeComment, TraineeCommentKey> {

	Optional<TraineeComment> findById(TraineeCommentKey id);
	
	List<TraineeComment> findByTraineeCommentKeyTraineeId(Trainee traineeId);

	List<TraineeComment> findByTraineeCommentKeyClassId(Class classId);

	List<TraineeComment> findByTraineeCommentKeyModuleId(Module moduleId);
	
}