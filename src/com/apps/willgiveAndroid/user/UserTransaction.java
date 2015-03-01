package com.apps.willgiveAndroid.user;

public class UserTransaction {
	private String transactionId;
	private Long userId;
	private Long recipientId; //same as charityId 
	private Double amount;
	private String dateTime;
	private String settleTime;
	private String status;
	
	public UserTransaction(String transactionId, Long userId, Long recipientId,
			Double amount, String dateTime, String settleTime, String status) {
		super();
		this.transactionId = transactionId;
		this.userId = userId;
		this.recipientId = recipientId;
		this.amount = amount;
		this.dateTime = dateTime;
		this.settleTime = settleTime;
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "UserTransaction [userId=" + userId + ", transactionId="
				+ transactionId + ", recipientId=" + recipientId + ", amount="
				+ amount + ", dateTime=" + dateTime + ", settleTime="
				+ settleTime + ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((transactionId == null) ? 0 : transactionId.hashCode());
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
		UserTransaction other = (UserTransaction) obj;
		if (transactionId == null) {
			if (other.transactionId != null)
				return false;
		} else if (!transactionId.equals(other.transactionId))
			return false;
		return true;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Long getRecipientId() {
		return recipientId;
	}
	public void setRecipientId(Long recipientId) {
		this.recipientId = recipientId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getSettleTime() {
		return settleTime;
	}
	public void setSettleTime(String settleTime) {
		this.settleTime = settleTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
