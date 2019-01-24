package web.info.LogisticsInfoJiaCheng.service;

import web.info.LogisticsInfoJiaCheng.pojo.TscJcexStatusDetail;

import java.util.List;

public interface TscJcexStatusDetailService {

    /**
     * 存入轨迹信息
     */
    int saveTscJcexStatusDetail(List<TscJcexStatusDetail> tscJcexStatusDetail);

}
