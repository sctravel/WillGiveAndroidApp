package com.apps.willgiveAndroid.charity;

public class CharityQRCode {
	private String prefix;
	private String EIN;
	private String name;
	private String address;
	private String phone;
	private String mission;
	private String expireDate;
	
	
	public CharityQRCode(String prefix, String eIN, String name,
			String address, String phone, String mission, String expireDate) {
		super();
		this.prefix = prefix;
		this.EIN = eIN;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.mission = mission;
		this.expireDate = expireDate;
	}
	
	
	
	@Override
	public String toString() {
		return "CharityQRCodeContent [prefix=" + prefix + ", EIN=" + EIN
				+ ", name=" + name + ", phone=" + phone + ", address="
				+ address + ", mission=" + mission + ", expireDate="
				+ expireDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((EIN == null) ? 0 : EIN.hashCode());
		result = prime * result
				+ ((expireDate == null) ? 0 : expireDate.hashCode());
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
		CharityQRCode other = (CharityQRCode) obj;
		if (EIN == null) {
			if (other.EIN != null)
				return false;
		} else if (!EIN.equals(other.EIN))
			return false;
		if (expireDate == null) {
			if (other.expireDate != null)
				return false;
		} else if (!expireDate.equals(other.expireDate))
			return false;
		return true;
	}



	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getEIN() {
		return EIN;
	}
	public void setEIN(String eIN) {
		EIN = eIN;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMission() {
		return mission;
	}
	public void setMission(String mission) {
		this.mission = mission;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	
	
}
