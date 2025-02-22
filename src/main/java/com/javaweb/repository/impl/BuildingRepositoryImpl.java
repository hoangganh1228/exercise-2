package com.javaweb.repository.impl;

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

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

import utils.NumberUntil;
import utils.StringUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	static final String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
	static final String USER = "root";
	static final String PASS = "Mo@28122004";
	
	public static void joinTable(Map<String, Object> params, List<String> typeCode , StringBuilder sql) {
		String staffId = (String)params.get("staffId");
		if(StringUtil.checkString("staffId")) {
			sql.append(" INNER JOIN assignmentbuilding asb ON b.id = asb.buildingid ");
		}
		if(typeCode != null && typeCode.size() != 0) {
			sql.append(" INNER JOIN buildingrenttype brt ON b.id = brt.buildingid ");
			sql.append(" INNER JOIN renttype rt ON brt.renttypeid = rt.id ");
		}
		
		String rentAreaTo = (String)params.get("areaTo");
		String rentAreaFrom = (String)params.get("areaFrom");
		if(StringUtil.checkString(rentAreaFrom) || StringUtil.checkString(rentAreaTo)) {
			sql.append(" INNER JOIN rentarea ra ON b.id = ra.buildingid ");
		}
	}
	
	public static void queryNormal(Map<String, Object> params, StringBuilder where) {
		
		for(Map.Entry<String, Object> it : params.entrySet()) {
			
			if(!it.getKey().equals("staffId") && !it.getKey().equals("typeCode") && !it.getKey().startsWith("area") && !it.getKey().startsWith("rentPrice")) {
				String value = it.getValue().toString();
				
				if(StringUtil.checkString(value)) {
					
					if(NumberUntil.isNumber(value)) {
						where.append(" AND b." + it.getKey() + " = " + value + " ");
					} else {
						where.append(" AND b." + it.getKey() + " LIKE '%" + value + "%' ");
					}
				}
			}
			
		}
	}
	
	public static void querySpecial(Map<String, Object> params, List<String> typeCode ,StringBuilder where) {
		String staffId = (String)params.get("staffId");
		if(StringUtil.checkString(staffId)) {
			where.append(" AND asb.staffid = " + staffId + " ");
		} 
		String rentAreaFrom = (String)params.get("areaFrom");
		String rentAreaTo = (String)params.get("areaTo");
		if(StringUtil.checkString(rentAreaFrom) == true || StringUtil.checkString(rentAreaTo) == true) {
			if(StringUtil.checkString(rentAreaFrom)) {
				where.append(" AND ra.value >= " + rentAreaFrom);
			}
			if(StringUtil.checkString(rentAreaTo)) {
				where.append(" AND ra.value <= " + rentAreaTo);
			}
		}
		String rentPriceFrom = (String)params.get("rentPriceFrom");
		String rentPriceTo = (String)params.get("rentPriceTo");
		if(StringUtil.checkString(rentAreaFrom) || StringUtil.checkString(rentAreaTo)) {
			if(StringUtil.checkString(rentPriceFrom)) {
				where.append(" AND b.rentprice >= " + rentPriceFrom);
			}
			if(StringUtil.checkString(rentAreaTo)) {
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
		if(typeCode != null && typeCode.size() != 0) {
			where.append(" AND(");
			String sql = typeCode.stream().map(it -> "rt.code LIKE" + "'%" + it + "%' ").collect(Collectors.joining(" OR "));
			where.append(sql);
			where.append(" ) ");
		}
		
	}
	
	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params, List<String> typeCode) {
		// TODO Auto-generated method stub
		
		StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.districtid, b.street, b.ward, b.numberofbasement, b.rentprice, b.floorarea, b.servicefee, b.brokeragefee, b.managername, b.managerphonenumber, b.direction, b.brokeragefee, b.servicefee FROM building b");
		joinTable(params, typeCode, sql);
		StringBuilder where = new StringBuilder(" WHERE 1 = 1 ");
		queryNormal(params, where);
		querySpecial(params, typeCode, where);
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
