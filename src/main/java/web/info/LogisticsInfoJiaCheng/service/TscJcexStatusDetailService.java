package web.info.LogisticsInfoJiaCheng.service;

import web.info.LogisticsInfoJiaCheng.pojo.TscJcexStatusDetail;

public interface TscJcexStatusDetailService {

    /**
     * 存入轨迹信息
     */
    int saveTscJcexWaybillNumber(TscJcexStatusDetail tscJcexStatusDetail);

}
