package web.info.LogisticsInfoJiaCheng.service;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexWaybillNumber;

import java.util.List;

public interface TscJcexWaybillNumberService {

    /**
     * 查询订单还没签收的订单信息
     */

    List<TscJcexWaybillNumber> tscJcexStatusNumberList();


    /**
     * 如果订单已签收 更新单号状态
     * @param wbnId
     * @return
     */
    int upTscJcexWaybillNumberStatus(Long wbnId);

    /**
     * 更新单号表信息
     * @param tscJcexWaybillNumber
     * @return
     */
    int upTscJcexWaybillNumberInfo(TscJcexWaybillNumber tscJcexWaybillNumber);
}
