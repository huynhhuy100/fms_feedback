package vn.com.r2s.fms.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.com.r2s.fms.api.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String>{

	
}
