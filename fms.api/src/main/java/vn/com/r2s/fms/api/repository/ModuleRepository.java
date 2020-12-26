package vn.com.r2s.fms.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.com.r2s.fms.api.entity.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer>{
    Module findModuleByModuleName(String moduleName);
	
}
