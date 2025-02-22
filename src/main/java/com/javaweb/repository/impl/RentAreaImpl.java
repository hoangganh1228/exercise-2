package com.javaweb.repository.impl;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.RentAreaEnity;

import utils.ConnectionJDBCUtil;
@Repository
public class RentAreaImpl implements RentAreaRepository {

	@Override
	public List<RentAreaEnity> getValueByBuildingId(Integer id) {
		String sql = "SELECT ra.value FROM rentarea ra WHERE ra.buildingid = " + id;
//		System.out.println(sql);
		List<RentAreaEnity> rentAreas = new ArrayList<>();
		try(Connection conn = ConnectionJDBCUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while(rs.next()) {
				RentAreaEnity areaEnity = new RentAreaEnity();
				areaEnity.setValue(rs.getString("value"));
				rentAreas.add(areaEnity);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return rentAreas;
	}

}
