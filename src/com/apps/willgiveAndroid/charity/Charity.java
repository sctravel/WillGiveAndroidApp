package com.apps.willgiveAndroid.charity;

public class Charity {
	
	private Long id;
	private String email;
	private String name;
	private String EIN;
	private String category;
	private String address;
	private String city;
	private String state;
	private String country;
	private String zipcode;
	private String phone;
	private String fax;
	private String mission;
	private String imageUrl;
	private String videoUrl;
	private String website;
	private String facebookUrl;
	private Double rating;
	
	//not used for now
	private String status;
	private String contactPersonName;
	private String contactPersonTitle;
	
	public Charity(Long id, String email, String name, String eIN,
			String category, String address, String city, String state,
			String country, String zipcode, String phone, String fax,
			String mission, String imageUrl, String videoUrl, String website,
			String facebookUrl, Double rating, String status,
			String contactPersonName, String contactPersonTitle) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		EIN = eIN;
		this.category = category;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipcode = zipcode;
		this.phone = phone;
		this.fax = fax;
		this.mission = mission;
		this.imageUrl = imageUrl;
		this.videoUrl = videoUrl;
		this.website = website;
		this.facebookUrl = facebookUrl;
		this.rating = rating;
		this.status = status;
		this.contactPersonName = contactPersonName;
		this.contactPersonTitle = contactPersonTitle;
	}
	//Id is the only identifier to determine whether two charities are the same
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Charity other = (Charity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEIN() {
		return EIN;
	}
	public void setEIN(String eIN) {
		EIN = eIN;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getMission() {
		return mission;
	}
	public void setMission(String mission) {
		this.mission = mission;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getFacebookUrl() {
		return facebookUrl;
	}
	public void setFacebookUrl(String facebookUrl) {
		this.facebookUrl = facebookUrl;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getContactPersonName() {
		return contactPersonName;
	}
	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}
	public String getContactPersonTitle() {
		return contactPersonTitle;
	}
	public void setContactPersonTitle(String contactPersonTitle) {
		this.contactPersonTitle = contactPersonTitle;
	}
	

}
