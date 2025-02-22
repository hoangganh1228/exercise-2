package com.javaweb.repository;

import com.javaweb.repository.entity.DistrictEnity;

public interface DistrictRepository {
	DistrictEnity findNameById(Integer id);
}
