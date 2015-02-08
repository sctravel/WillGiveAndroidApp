package com.apps.willgiveAndroid.user;

public class UserSettings {
	
	private Long userId;
	
	private Long maxAmountDaily;
	private Long maxAmountPerTime;
	private Long defaultAmountPerTime;
	
	private Boolean receiveEmailUpdate;
	private Boolean receiveEmailNotification;
	private Boolean allowContributionPublic;	
	
	public UserSettings(Long userId, Long maxAmountDaily,
			Long maxAmountPerTime, Long defaultAmountPerTime,
			Boolean receiveEmailUpdate, Boolean receiveEmailNotification,
			Boolean allowContributionPublic) {
		super();
		this.userId = userId;
		this.maxAmountDaily = maxAmountDaily;
		this.maxAmountPerTime = maxAmountPerTime;
		this.defaultAmountPerTime = defaultAmountPerTime;
		this.receiveEmailUpdate = receiveEmailUpdate;
		this.receiveEmailNotification = receiveEmailNotification;
		this.allowContributionPublic = allowContributionPublic;
	}

	
	
	@Override
	public String toString() {
		return "UserSettings [userId=" + userId + ", maxAmountDaily="
				+ maxAmountDaily + ", maxAmountPerTime=" + maxAmountPerTime
				+ ", defaultAmountPerTime=" + defaultAmountPerTime
				+ ", receiveEmailUpdate=" + receiveEmailUpdate
				+ ", receiveEmailNotification=" + receiveEmailNotification
				+ ", allowContributionPublic=" + allowContributionPublic + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		UserSettings other = (UserSettings) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}



	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getMaxAmountDaily() {
		return maxAmountDaily;
	}
	public void setMaxAmountDaily(Long maxAmountDaily) {
		this.maxAmountDaily = maxAmountDaily;
	}
	public Long getMaxAmountPerTime() {
		return maxAmountPerTime;
	}
	public void setMaxAmountPerTime(Long maxAmountPerTime) {
		this.maxAmountPerTime = maxAmountPerTime;
	}
	public Long getDefaultAmountPerTime() {
		return defaultAmountPerTime;
	}
	public void setDefaultAmountPerTime(Long defaultAmountPerTime) {
		this.defaultAmountPerTime = defaultAmountPerTime;
	}
	public Boolean getReceiveEmailUpdate() {
		return receiveEmailUpdate;
	}
	public void setReceiveEmailUpdate(Boolean receiveEmailUpdate) {
		this.receiveEmailUpdate = receiveEmailUpdate;
	}
	public Boolean getReceiveEmailNotification() {
		return receiveEmailNotification;
	}
	public void setReceiveEmailNotification(Boolean receiveEmailNotification) {
		this.receiveEmailNotification = receiveEmailNotification;
	}
	public Boolean getAllowContributionPublic() {
		return allowContributionPublic;
	}
	public void setAllowContributionPublic(Boolean allowContributionPublic) {
		this.allowContributionPublic = allowContributionPublic;
	}
	
	
	

}
