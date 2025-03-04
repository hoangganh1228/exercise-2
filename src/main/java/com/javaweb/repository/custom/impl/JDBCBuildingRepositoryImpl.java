package com.javaweb.repository.custom.impl;

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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.custom.BuildingRepositoryCustom;
import com.javaweb.repository.entity.BuildingEntity;

import utils.NumberUntil;
import utils.StringUtil;

@Repository
@Primary
public class JDBCBuildingRepositoryImpl implements BuildingRepositoryCustom  {
//	@Value("${spring.datasource.url}")
//	private String DB_URL;
//	@Value("${spring.datasource.username}")
//	private String USER;
//	@Value("${spring.datasource.password}")
//	private String PASS;
	
	@PersistenceContext
	private EntityManager entityManager;
	
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
	
//	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		// TODO Auto-generated method stub
//		System.out.println(buildingSearchBuilder);
		StringBuilder sql = new StringBuilder("SELECT b.* FROM building b");
		joinTable(buildingSearchBuilder, sql);
		StringBuilder where = new StringBuilder(" WHERE 1 = 1 ");
		queryNormal(buildingSearchBuilder, where);
		querySpecial(buildingSearchBuilder, where);
		where.append("GROUP BY b.id");
		sql.append(where);
		System.out.println(sql);
		Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
		
		return query.getResultList();
		
	}
	
}
