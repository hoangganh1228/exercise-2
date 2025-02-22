package com.javaweb.repository;

import java.util.List;

import com.javaweb.repository.entity.RentAreaEnity;

public interface RentAreaRepository {
	List<RentAreaEnity> getValueByBuildingId(Integer id);
}
