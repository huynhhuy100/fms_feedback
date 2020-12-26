package vn.com.r2s.fms.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.com.r2s.fms.api.entity.Admin;
import vn.com.r2s.fms.api.entity.Trainer;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, String>{

	
}
