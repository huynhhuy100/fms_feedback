package vn.com.r2s.fms.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.com.r2s.fms.api.entity.Admin;
import vn.com.r2s.fms.api.entity.TypeFeedback;

@Repository
public interface TypeFeedbackRepository extends JpaRepository<TypeFeedback, Integer>{

	
}
