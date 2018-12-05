package web.info.LogisticsInfoJiaCheng.pojo;


public class TransPortCompany {
  /**
   * 标识ID
   */
  private Long tscId;
  /**
   * 公司编号
   */
  private String transportCompanyNumber;
  /**
   * 公司简称
   */
  private String transportCompanyName;
  /**
   * 公司全称
   */
  private String transportCompanyFullName;
  /**
   * 请求时间间隔
   */
  private Long requestInterval;
  /**
   * 请求开始时间
   */
  private Long requestStarTime;
  /**
   * 请求频次
   */
  private Long requestFrequency;
  /**
   * 请求频次单位
   */
  private String requestFrequencyUnit;
  /**
   * 备注
   */
  private String remark;


  public Long getTscId() {
    return tscId;
  }

  public void setTscId(Long tscId) {
    this.tscId = tscId;
  }

  public String getTransportCompanyNumber() {
    return transportCompanyNumber;
  }

  public void setTransportCompanyNumber(String transportCompanyNumber) {
    this.transportCompanyNumber = transportCompanyNumber;
  }

  public String getTransportCompanyName() {
    return transportCompanyName;
  }

  public void setTransportCompanyName(String transportCompanyName) {
    this.transportCompanyName = transportCompanyName;
  }

  public String getTransportCompanyFullName() {
    return transportCompanyFullName;
  }

  public void setTransportCompanyFullName(String transportCompanyFullName) {
    this.transportCompanyFullName = transportCompanyFullName;
  }

  public Long getRequestInterval() {
    return requestInterval;
  }

  public void setRequestInterval(Long requestInterval) {
    this.requestInterval = requestInterval;
  }

  public Long getRequestStarTime() {
    return requestStarTime;
  }

  public void setRequestStarTime(Long requestStarTime) {
    this.requestStarTime = requestStarTime;
  }

  public Long getRequestFrequency() {
    return requestFrequency;
  }

  public void setRequestFrequency(Long requestFrequency) {
    this.requestFrequency = requestFrequency;
  }

  public String getRequestFrequencyUnit() {
    return requestFrequencyUnit;
  }

  public void setRequestFrequencyUnit(String requestFrequencyUnit) {
    this.requestFrequencyUnit = requestFrequencyUnit;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
