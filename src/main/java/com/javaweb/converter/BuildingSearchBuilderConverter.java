package com.javaweb.converter;

import java.util.List;


import java.util.Map;

import org.springframework.stereotype.Component;

import com.javaweb.builder.BuildingSearchBuilder;

import utils.MapUtil;
@Component
public class BuildingSearchBuilderConverter {
	public BuildingSearchBuilder toBuildingSearchBuilder(Map<String, Object> params, List<String> typeCode) {
		BuildingSearchBuilder buildingSearchBuilder = new BuildingSearchBuilder.Builder()
				.setName(MapUtil.getObject(params, "name", String.class))
				.setWard(MapUtil.getObject(params, "ward", String.class))
				.setFloorArea(MapUtil.getObject(params, "floorArea", Integer.class))
				.setStreet(MapUtil.getObject(params, "street", String.class))
				.setWard(MapUtil.getObject(params, "ward", String.class))
				.setDistrictId(MapUtil.getObject(params, "districtId", Integer.class))
				.setStructure(MapUtil.getObject(params, "structure", String.class))
				.setNumberOfBasement(MapUtil.getObject(params, "numberOfBasement", Integer.class))
				.setTypeCode(typeCode)
				.setManagerName(MapUtil.getObject(params, "managerName", String.class))
				.setRentPriceFrom(MapUtil.getObject(params, "rentPriceFrom", Integer.class))
				.setRentPriceTo(MapUtil.getObject(params, "rentPriceTo", Integer.class))
				.setAreaFrom(MapUtil.getObject(params, "areaFrom", Integer.class))
				.setAreaTo(MapUtil.getObject(params, "areaTo", Integer.class))
				.setStaffId(MapUtil.getObject(params, "staffId", Integer.class))
				.build();
		return buildingSearchBuilder;
	}
	
																		
}
