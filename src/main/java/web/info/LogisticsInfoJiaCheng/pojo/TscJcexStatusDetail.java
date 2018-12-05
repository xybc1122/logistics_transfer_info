package web.info.LogisticsInfoJiaCheng.pojo;


public class TscJcexStatusDetail {
  /**
   * 标识id
   */
  private Long id;
  /**
   * 运单号ID
   */
  private Long wbnId;
  /**
   * 发生状态
   */
  private String status;
  /**
   * 发生时间
   */
  private Long time;
  /**
   * 发生地点
   */
  private String locate;
  /**
   * 详细内容
   */
  private String statusCnName;

  public TscJcexStatusDetail(){

  }
  public TscJcexStatusDetail(String status, Long time, String locate, String statusCnName) {
    this.status = status;
    this.time = time;
    this.locate = locate;
    this.statusCnName = statusCnName;
  }



  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getWbnId() {
    return wbnId;
  }

  public void setWbnId(Long wbnId) {
    this.wbnId = wbnId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Long getTime() {
    return time;
  }

  public void setTime(Long time) {
    this.time = time;
  }

  public String getLocate() {
    return locate;
  }

  public void setLocate(String locate) {
    this.locate = locate;
  }

  public String getStatusCnName() {
    return statusCnName;
  }

  public void setStatusCnName(String statusCnName) {
    this.statusCnName = statusCnName;
  }

  @Override
  public String toString() {
    return "TscJcexStatusdetail{" +
            "id=" + id +
            ", wbnId=" + wbnId +
            ", status='" + status + '\'' +
            ", time=" + time +
            ", locate='" + locate + '\'' +
            ", statusCnName='" + statusCnName + '\'' +
            '}';
  }
}
