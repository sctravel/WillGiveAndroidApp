package com.apps.willgiveAndroid.charity;

public class TransactionSummaryForCharity {
	private Long charityId;
	private Long duration; //Last x days (0 means all the period)
	private Double totalAmount;
	private Long totalCount;
	
	
	public TransactionSummaryForCharity(Long charityId, Long duration,
			Double totalAmount, Long totalCount) {
		super();
		this.charityId = charityId;
		this.duration = duration;
		this.totalAmount = totalAmount;
		this.totalCount = totalCount;
	}
	
	@Override
	public String toString() {
		return "TransactionSummaryForCharity [charityId=" + charityId
				+ ", duration=" + duration + ", totalAmount=" + totalAmount
				+ ", totalCount=" + totalCount + "]";
	}
	public Long getCharityId() {
		return charityId;
	}
	public void setCharityId(Long charityId) {
		this.charityId = charityId;
	}
	public Long getDuration() {
		return duration;
	}
	
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	
	

}
