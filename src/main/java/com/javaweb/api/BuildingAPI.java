package com.javaweb.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.BuildingRequestDTO;
import com.javaweb.repository.entity.DistrictEnity;
import com.javaweb.service.BuildingService;

@RestController
@PropertySource("classpath:application.properties")

@Transactional
public class BuildingAPI {
	@Autowired
	private BuildingService buildingService;
	
	@Autowired
	private BuildingRepository buildingRepository;
	
	@Value("${dev.nguyen}")
	private String data;
	
	@PersistenceContext
	private EntityManager entityManager;
	
    @GetMapping(value="/api/building/")
    public List<BuildingDTO> getBuilding(@RequestParam Map<String, Object> params,
    									@RequestParam(name="typeCode", required = false) List<String> typeCode) {
    	System.out.println("Params: " + params);
        System.out.println("typeCode: " + typeCode);
    	List<BuildingDTO> result = buildingService.findAll(params, typeCode);
        return result;
    }
    
    @GetMapping(value="/api/building/{name}")
    public List<BuildingDTO> getBuilding(@PathVariable String name) {
    	List<BuildingDTO > result = new ArrayList<>();
    	BuildingEntity a = buildingRepository.findByNameContaining(name);
    	System.out.println(a);
    	return result;
    }
    
    @GetMapping(value="/api/building/{name}/{street}")
    public List<BuildingDTO> getBuilding(@PathVariable String name, @PathVariable String street) {
    	List<BuildingDTO > result = new ArrayList<>();
    	BuildingEntity a = buildingRepository.findByNameContainingAndStreet(name, street);
    	System.out.println(a);
    	return result;
    }
    
    @PostMapping(value="/api/building/")
    public void createBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
    	BuildingEntity builEntity = new BuildingEntity();
    	builEntity.setName(buildingRequestDTO.getName());
    	builEntity.setStreet(buildingRequestDTO.getStreet());
    	builEntity.setWard(buildingRequestDTO.getWard());
    	DistrictEnity districtEntity = new DistrictEnity();
    	districtEntity.setId(buildingRequestDTO.getDistrictId());
    	builEntity.setDistrict(districtEntity);
    	buildingRepository.save(builEntity);
    	System.out.println("OK");
    }
    
    @PutMapping(value="/api/building/")
    public void updateBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
    	BuildingEntity buildingEntity = new BuildingEntity();
    	buildingEntity.setId(2);
    	buildingEntity.setName(buildingRequestDTO.getName());
    	buildingEntity.setStreet(buildingRequestDTO.getStreet());
    	buildingEntity.setWard(buildingRequestDTO.getWard());
    	DistrictEnity districtEntity = new DistrictEnity();
    	districtEntity.setId(buildingRequestDTO.getDistrictId());
    	buildingEntity.setDistrict(districtEntity);
    	entityManager.merge(buildingEntity);
    	System.out.println("OK");
    }
    
    @DeleteMapping(value="/api/building/{ids }")
    public void deleteBuilding(@PathVariable Integer[] ids) {
//    	buildingRepository.findAll(buildingSearchBuilder)(ids);
    	
    }
    
    
}
