package com.excel.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "map_location")
public class TempTable implements Serializable {

	private static final long serialVersionUID = -6594630833024506084L;

	@Id
	@Column(name = "id", length = 20)
	private String id;

	@Column(name = "address", length = 20)
	private String address;

	@Column(name = "city", length = 20)
	private String city;

	@Column(name = "lat", length = 20)
	private String lat;

	@Column(name = "lng", length = 20)
	private String lng;

	@Column(name = "re_status", length = 20)
	private String status;

	@Column(name = "confidence", length = 20)
	private String confidence;

	@Column(name = "precise", length = 20)
	private String precise;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getConfidence() {
		return confidence;
	}

	public void setConfidence(String confidence) {
		this.confidence = confidence;
	}

	public String getPrecise() {
		return precise;
	}

	public void setPrecise(String precise) {
		this.precise = precise;
	}

}