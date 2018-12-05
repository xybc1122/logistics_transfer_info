package web.info.LogisticsInfoJiaCheng.service;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexWaybillNumber;

import java.util.List;

public interface TscJcexWaybillNumberService {

    /**
     * 查询订单还没签收的订单信息
     */

    List<TscJcexWaybillNumber> tscJcexStatusNumberList();



    int upTscJcexWaybillNumber(Long wbnId);
}
