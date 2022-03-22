package com.modestcarz.modestcarzbootapp.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name = "modestCars")
@Table(name = "TBL_MODEST_CARS")
public class Cars implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long car_id;
	private String type;
	private String make;
	private String model;
	private String regNo;
	private String mileage;
	private String fuelType;
	private String engineSize;
	private String power;
	private String gearBox;
	private String noOfSeats;
	private String doors;
	private String color;
	private String desc;
	private String extras;
	private String isFeatured;
	private Long cutPrice;
	private Long actualPrice;
	private Date postDate;
	
	private List<String> imageUrls;
	
	
	@Transient
	public List<String> getImageUrls() {
		return imageUrls;
	}
	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "TMC_ID")
	public Long getCar_id() {
		return car_id;
	}
	public void setCar_id(Long car_id) {
		this.car_id = car_id;
	}
	@Column(name = "TMC_MAKE", nullable = true)
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	@Column(name = "TMC_MODEL", nullable = true)
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	@Column(name = "TMC_REG_NO", nullable = true)
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	@Column(name = "TMC_MILEAGE", nullable = true)
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	@Column(name = "TMC_FUEL_TYPE", nullable = true)
	public String getFuelType() {
		return fuelType;
	}
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}
	@Column(name = "TMC_ENGINE_SIZE", nullable = true)
	public String getEngineSize() {
		return engineSize;
	}
	public void setEngineSize(String engineSize) {
		this.engineSize = engineSize;
	}
	@Column(name = "TMC_POWER", nullable = true)
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	@Column(name = "TMC_GEARBOX", nullable = true)
	public String getGearBox() {
		return gearBox;
	}
	public void setGearBox(String gearBox) {
		this.gearBox = gearBox;
	}
	@Column(name = "TMC_NO_OF_SEATS", nullable = true)
	public String getNoOfSeats() {
		return noOfSeats;
	}
	public void setNoOfSeats(String noOfSeats) {
		this.noOfSeats = noOfSeats;
	}
	@Column(name = "TMC_DOORS", nullable = true)
	public String getDoors() {
		return doors;
	}
	public void setDoors(String doors) {
		this.doors = doors;
	}
	@Column(name = "TMC_COLOR", nullable = true)
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	@Column(name = "TMC_CUT_PRICE", nullable = true)
	public Long getCutPrice() {
		return cutPrice;
	}
	public void setCutPrice(Long cutPrice) {
		this.cutPrice = cutPrice;
	}
	@Column(name = "TMC_ACTUAL_PRICE", nullable = true)
	public Long getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(Long actualPrice) {
		this.actualPrice = actualPrice;
	}
	@Column(name = "TMC_POST_DATE", nullable = true)
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	@Column(name = "TMC_IS_FEATURED", nullable = true)
	public String getIsFeatured() {
		return isFeatured;
	}
	public void setIsFeatured(String isFeatured) {
		this.isFeatured = isFeatured;
	}
	@Column(name = "TMC_DESC", nullable = true)
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Column(name = "TMC_EXTRAS", nullable = true)
	public String getExtras() {
		return extras;
	}
	public void setExtras(String extras) {
		this.extras = extras;
	}
	
	@Override
	public String toString() {
		return "Cars [car_id=" + car_id + ", make=" + make + ", model=" + model + ", regNo=" + regNo + ", mileage="
				+ mileage + ", fuelType=" + fuelType + ", engineSize=" + engineSize + ", power=" + power + ", gearBox="
				+ gearBox + ", noOfSeats=" + noOfSeats + ", doors=" + doors + ", color=" + color + ", desc=" + desc
				+ ", extras=" + extras + ", isFeatured=" + isFeatured + ", cutPrice=" + cutPrice + ", actualPrice="
				+ actualPrice + ", postDate=" + postDate + ", imageUrls=" + imageUrls + "]";
	}
	@Column(name = "TMC_TYPE", nullable = true)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
