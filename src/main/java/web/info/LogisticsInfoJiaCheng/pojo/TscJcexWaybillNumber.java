package web.info.LogisticsInfoJiaCheng.pojo;


public class TscJcexWaybillNumber {
  /**
   * ID
   */
  private Long wbnId;
  /**
   * 运单号
   */
  private String waybillNumber;
  /**
   * 到达国家
   */
  private String recipientCountry;
  /**
   * 实重
   */
  private Double totalWeight;
  /**
   * 计费重
   */
  private Double chargeWeight;
  /**
   * 柴积重
   */
  private Double checkInVolumeWeight;
  /**
   * 是否妥投(0未结束，1已完成)
   */
  private Integer status;
  /**
   * 客户ID
   */
  private Long customerId;
  /**
   * 最近更新时间
   */
  private Long lastTime;

  public Long getWbnId() {
    return wbnId;
  }

  public void setWbnId(Long wbnId) {
    this.wbnId = wbnId;
  }

  public String getWaybillNumber() {
    return waybillNumber;
  }

  public void setWaybillNumber(String waybillNumber) {
    this.waybillNumber = waybillNumber;
  }

  public String getRecipientCountry() {
    return recipientCountry;
  }

  public void setRecipientCountry(String recipientCountry) {
    this.recipientCountry = recipientCountry;
  }

  public Double getTotalWeight() {
    return totalWeight;
  }

  public void setTotalWeight(Double totalWeight) {
    this.totalWeight = totalWeight;
  }

  public Double getChargeWeight() {
    return chargeWeight;
  }

  public void setChargeWeight(Double chargeWeight) {
    this.chargeWeight = chargeWeight;
  }

  public Double getCheckInVolumeWeight() {
    return checkInVolumeWeight;
  }

  public void setCheckInVolumeWeight(Double checkInVolumeWeight) {
    this.checkInVolumeWeight = checkInVolumeWeight;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public Long getLastTime() {
    return lastTime;
  }

  public void setLastTime(Long lastTime) {
    this.lastTime = lastTime;
  }

  @Override
  public String toString() {
    return "TscJcexWaybillNumber{" +
            "wbnId=" + wbnId +
            ", waybillNumber='" + waybillNumber + '\'' +
            ", recipientCountry='" + recipientCountry + '\'' +
            ", totalWeight=" + totalWeight +
            ", chargeWeight=" + chargeWeight +
            ", checkInVolumeWeight=" + checkInVolumeWeight +
            ", status=" + status +
            ", customerId=" + customerId +
            ", lastTime=" + lastTime +
            '}';
  }
}
