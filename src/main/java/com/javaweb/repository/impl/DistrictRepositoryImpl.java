package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEnity;
@Repository
public class DistrictRepositoryImpl implements DistrictRepository {
	static final String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
	static final String USER = "root";
	static final String PASS = "Mo@28122004";
	
	@Override
	public DistrictEnity findNameById(Integer id) {
//		System.out.println(id);
		String sql = "SELECT d.name FROM district d WHERE d.id = " + id;
		DistrictEnity districtEnity = new DistrictEnity();
		
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while(rs.next()) {
				districtEnity.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connected failed");
		}
		
		return districtEnity;
	}
	
	
}
