package vn.com.r2s.fms.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.com.r2s.fms.api.entity.Answer;
import vn.com.r2s.fms.api.entity.AnswerKey;
import vn.com.r2s.fms.api.entity.Assignment;
import vn.com.r2s.fms.api.entity.AssignmentKey;
import vn.com.r2s.fms.api.entity.Trainer;
import vn.com.r2s.fms.api.entity.Class;
import vn.com.r2s.fms.api.entity.Module;
import vn.com.r2s.fms.api.entity.Question;
import vn.com.r2s.fms.api.entity.Trainee;
@Repository
public interface AnswerRepository extends JpaRepository<Answer, AnswerKey> {

	Optional<Answer> findById(AnswerKey id);
	
	List<Answer> findByAnswerKeyTraineeId(Trainee traineeId);

	List<Answer> findByAnswerKeyClassId(Class classId);

	List<Answer> findByAnswerKeyModuleId(Module moduleId);
	
	List<Answer> findByAnswerKeyQuestionID(Question questionID);
}