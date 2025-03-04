package com.javaweb.repository.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "building")
public class BuildingEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "street")
	private String street;
	
	@Column(name = "ward")
	private String ward;
	
	
	@Column(name = "structure")
	private String structure;
	
	@Column(name = "numberofbasement")
	private Integer numberofbasement;
	
	@Column(name = "floorarea")
	private Integer floorarea;
	
	@Column(name = "direction")
	private String direction;
	
	@Column(name = "level")
	private Integer level;
	
	@Column(name = "rentprice")
	private Integer rentprice;
	
	@Column(name = "rentpricedescription")
	private String rentpricedescription;
	
	@Column(name = "servicefee")
	private Integer servicefee;
	
	@Column(name = "carfee")
	private Integer carfee;
	
	@Column(name = "motorbikefee")
	private Integer motorbikefee;
	
	@Column(name = "overtimefee")
	private Integer overtimefee;
	
	@Column(name = "waterfee")
	private Integer waterfee;
	
	@Column(name = "electricityfee")
	private Integer electricityfee;
	
	@Column(name = "deposit")
	private Integer deposit;
	
	@Column(name = "payment")
	private Integer payment;
	
	@Column(name = "renttime")
	private Integer renttime;
	
	@Column(name = "decorationtime")
	private Integer decorationtime;
	
	@Column(name = "brokeragefee")
	private Float brokeragefee;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "linkofbuilding")
	private String linkofbuilding;
	
	@Column(name = "map")
	private String map;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "createddate")
	private Date createddate;
	
	@Column(name = "modifieddate")
	private Date modifieddate;
	
	@Column(name = "createdby")
	private String createdby;
	
	@Column(name = "modifiedby")
	private String modifiedby;
	
	@Column(name = "managername")
	private String managername;
	
	@Column(name = "managerphonenumber")
	private String managerphonenumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "districtid")
	private DistrictEnity district;	
	
	@OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
	private List<RentAreaEnity> rents = new ArrayList<>();
	
	
	public List<RentAreaEnity> getRents() {
		return rents;
	}
	public void setRents(List<RentAreaEnity> rents) {
		this.rents = rents;
	}
	public DistrictEnity getDistrict() {
		return district;
	}
	public void setDistrict(DistrictEnity district) {
		this.district = district;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	
	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
	public Integer getNumberofbasement() {
		return numberofbasement;
	}
	public void setNumberofbasement(Integer numberofbasement) {
		this.numberofbasement = numberofbasement;
	}
	public Integer getFloorarea() {
		return floorarea;
	}
	public void setFloorarea(Integer floorarea) {
		this.floorarea = floorarea;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getRentprice() {
		return rentprice;
	}
	public void setRentprice(Integer rentprice) {
		this.rentprice = rentprice;
	}
	public String getRentpricedescription() {
		return rentpricedescription;
	}
	public void setRentpricedescription(String rentpricedescription) {
		this.rentpricedescription = rentpricedescription;
	}
	public Integer getServicefee() {
		return servicefee;
	}
	public void setServicefee(Integer servicefee) {
		this.servicefee = servicefee;
	}
	public Integer getCarfee() {
		return carfee;
	}
	public void setCarfee(Integer carfee) {
		this.carfee = carfee;
	}
	public Integer getMotorbikefee() {
		return motorbikefee;
	}
	public void setMotorbikefee(Integer motorbikefee) {
		this.motorbikefee = motorbikefee;
	}
	public Integer getOvertimefee() {
		return overtimefee;
	}
	public void setOvertimefee(Integer overtimefee) {
		this.overtimefee = overtimefee;
	}
	public Integer getWaterfee() {
		return waterfee;
	}
	public void setWaterfee(Integer waterfee) {
		this.waterfee = waterfee;
	}
	public Integer getElectricityfee() {
		return electricityfee;
	}
	public void setElectricityfee(Integer electricityfee) {
		this.electricityfee = electricityfee;
	}
	public Integer getDeposit() {
		return deposit;
	}
	public void setDeposit(Integer deposit) {
		this.deposit = deposit;
	}
	public Integer getPayment() {
		return payment;
	}
	public void setPayment(Integer payment) {
		this.payment = payment;
	}
	public Integer getRenttime() {
		return renttime;
	}
	public void setRenttime(Integer renttime) {
		this.renttime = renttime;
	}
	public Integer getDecorationtime() {
		return decorationtime;
	}
	public void setDecorationtime(Integer decorationtime) {
		this.decorationtime = decorationtime;
	}
	public Float getBrokeragefee() {
		return brokeragefee;
	}
	public void setBrokeragefee(Float brokeragefee) {
		this.brokeragefee = brokeragefee;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getLinkofbuilding() {
		return linkofbuilding;
	}
	public void setLinkofbuilding(String linkofbuilding) {
		this.linkofbuilding = linkofbuilding;
	}
	public String getMap() {
		return map;
	}
	public void setMap(String map) {
		this.map = map;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Date getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}
	public Date getModifieddate() {
		return modifieddate;
	}
	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}
	public String getManagername() {
		return managername;
	}
	public void setManagername(String managername) {
		this.managername = managername;
	}
	public String getManagerphonenumber() {
		return managerphonenumber;
	}
	public void setManagerphonenumber(String managerphonenumber) {
		this.managerphonenumber = managerphonenumber;
	}
	
	
}
