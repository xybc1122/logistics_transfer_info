package web.info.LogisticsInfoJiaCheng.pojo;


public class TscJcexOrderdetail {
  /**
   * 标识ID
   */
  private Long id;
  /**
   * 运单号ID
   */
  private Long wbnId;
  /**
   * 子运单号
   */
  private String childNumber;
  /**
   * 长
   */
  private Double length;
  /**
   * 宽
   */
  private Double width;
  /**
   * 高
   */
  private Double hight;
  /**
   * 重量
   */
  private Double weight;

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

  public String getChildNumber() {
    return childNumber;
  }

  public void setChildNumber(String childNumber) {
    this.childNumber = childNumber;
  }

  public Double getLength() {
    return length;
  }

  public void setLength(Double length) {
    this.length = length;
  }

  public Double getWidth() {
    return width;
  }

  public void setWidth(Double width) {
    this.width = width;
  }

  public Double getHight() {
    return hight;
  }

  public void setHight(Double hight) {
    this.hight = hight;
  }

  public Double getWeight() {
    return weight;
  }

  public void setWeight(Double weight) {
    this.weight = weight;
  }
}
