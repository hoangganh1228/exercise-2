package com.javaweb.repository.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

import utils.NumberUntil;
import utils.StringUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	static final String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
	static final String USER = "root";
	static final String PASS = "Mo@28122004";
	
	public static void joinTable(BuildingSearchBuilder buildingSearchBuilder , StringBuilder sql) {
		Integer staffId = buildingSearchBuilder.getStaffId();
		if(staffId != null) {
			sql.append(" INNER JOIN assignmentbuilding asb ON b.id = asb.buildingid ");
		}
		
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
		
		if(typeCode != null && typeCode.size() != 0) {
			sql.append(" INNER JOIN buildingrenttype brt ON b.id = brt.buildingid ");
			sql.append(" INNER JOIN renttype rt ON brt.renttypeid = rt.id ");
		}
		
		Integer rentAreaTo = buildingSearchBuilder.getRentPriceTo();
		Integer rentAreaFrom = buildingSearchBuilder.getRentPriceFrom();
		if(rentAreaTo != null || rentAreaFrom != null) {
			sql.append(" INNER JOIN rentarea ra ON b.id = ra.buildingid ");
		}
	}
	
	public static void queryNormal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
		
		try {
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
			
			for(Field item : fields) {
				item.setAccessible(true);
				String fieldName = item.getName();
				
				if(!fieldName.equals("staffId") && !fieldName.equals("typeCode") && !fieldName.startsWith("area") && !fieldName.startsWith("rentPrice")) {
					Object value = item.get(buildingSearchBuilder);
//					System.out.println(value);
					if(value != null) {
						if(item.getType().getName().equals("java.lang.Long") || item.getType().getName().equals("java.lang.Integer")) {
							where.append(" AND b." + fieldName + " = " + value + " ");
						} else if(item.getType().getName().equals("java.lang.String")) {
							where.append(" AND b." + fieldName + " LIKE '%" + value + "%' ");
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void querySpecial(BuildingSearchBuilder buildingSearchBuilder ,StringBuilder where) {
		Integer staffId = buildingSearchBuilder.getStaffId();
		if(staffId != null) {
			where.append(" AND asb.staffid = " + staffId + " ");
		} 
		Integer rentAreaFrom = buildingSearchBuilder.getAreaFrom();
		Integer rentAreaTo = buildingSearchBuilder.getAreaTo();
		if(rentAreaFrom != null|| rentAreaTo != null) {
			if(rentAreaFrom != null) {
				where.append(" AND ra.value >= " + rentAreaFrom);
			}
			if(rentAreaTo != null) {
				where.append(" AND ra.value <= " + rentAreaTo);
			}
		}
		Integer rentPriceFrom = buildingSearchBuilder.getRentPriceFrom();
		Integer rentPriceTo = buildingSearchBuilder.getRentPriceTo();
		if(rentPriceFrom != null|| rentPriceTo != null) {
			if(rentPriceFrom != null) {
				where.append(" AND b.rentprice >= " + rentPriceFrom);
			}
			if(rentPriceTo != null) {
				where.append(" AND b.rentprice <= " + rentAreaTo);
			}
		}
//		Java7
//		if(typeCode != null && typeCode.size() != 0) {
//			List<String> code = new ArrayList<>();
//			for(String item : typeCode) {
//				code.add("'" + item + "'");
//			}
//			where.append(" AND rt.code IN(" + String.join(",", code) + ") ");
//		}
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
		if(typeCode != null && typeCode.size() != 0) {
			where.append(" AND(");
			String sql = typeCode.stream().map(it -> "rt.code LIKE" + "'%" + it + "%' ").collect(Collectors.joining(" OR "));
			where.append(sql);
			where.append(" ) ");
		}
		
	}
	
	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		// TODO Auto-generated method stub
		System.out.println(buildingSearchBuilder);
		StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.districtid, b.street, b.ward, b.numberofbasement, b.rentprice, b.floorarea, b.servicefee, b.brokeragefee, b.managername, b.managerphonenumber, b.direction, b.brokeragefee, b.servicefee FROM building b");
		joinTable(buildingSearchBuilder, sql);
		StringBuilder where = new StringBuilder(" WHERE 1 = 1 ");
		queryNormal(buildingSearchBuilder, where);
		querySpecial(buildingSearchBuilder, where);
		sql.append(where);
		System.out.println(sql);
		List<BuildingEntity> result = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
    			Statement stmt = conn.createStatement();
    			ResultSet rs = stmt.executeQuery(sql.toString())) {
    		while(rs.next()) {
    			BuildingEntity building = new BuildingEntity();
    			building.setId(rs.getInt("b.id"));
    			building.setName(rs.getString("b.name"));
    			building.setFloorarea(rs.getInt("b.floorarea"));
    			building.setDistrictid(rs.getInt("b.districtid"));
    			building.setDirection(rs.getString("b.direction"));
    			building.setStreet(rs.getString("b.street"));
    			building.setWard(rs.getString("b.ward"));
    			building.setNumberofbasement(rs.getInt("b.numberofbasement")); 			
    			building.setManagername(rs.getString("b.managername"));
    			building.setManagerphonenumber(rs.getString("b.managerphonenumber"));
    			building.setRentprice(rs.getInt("b.rentprice"));
    			building.setServicefee(rs.getInt("b.servicefee"));
    			building.setBrokeragefee(rs.getFloat("brokeragefee"));
    			result.add(building);
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    		System.out.println("Connected failed");
    	}
		
		return result;
		
		
	}
	
}
