package web.info.LogisticsInfoJiaCheng.pojo;

public class LogisticsData {
    /**
     * 时间戳
     */
    private Long dateTime;
    /**
     * 令牌
     */
    private String token;
    /**
     * 当前页
     */
    private Integer currentPage;
    /**
     * 最大页
     */
    private Integer pageSize;

    /**
     * appKey
     */
    private String appKey;

    /**
     * md5Key
     */
    private String md5Key;

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
