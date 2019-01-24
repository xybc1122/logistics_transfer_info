package web.info.LogisticsInfoJiaCheng.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import web.info.LogisticsInfoJiaCheng.pojo.LogisticsData;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexOrderdetail;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexStatusDetail;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexWaybillNumber;

import java.util.List;

public interface TscJcexWaybillNumberService {
    /**
     * 查询实时物流数据
     *
     * @return
     */
    List<TscJcexWaybillNumber> realTimeInfo(LogisticsData logistics);

    /**
     * 查询订单还没签收的订单信息
     */

    List<TscJcexWaybillNumber> tscJcexStatusNumberList();


    /**
     * 如果订单已签收 更新单号状态
     *
     * @param wbnId
     * @return
     */
    int upTscJcexWaybillNumberStatus(Long wbnId);

    /**
     * 更新单号表信息
     *
     * @param tscJcexWaybillNumber
     * @return
     */
    int upTscJcexWaybillNumberInfo(TscJcexWaybillNumber tscJcexWaybillNumber);


    /**
     * 消费数据
     *
     * @return
     */
    void consumptionData(TscJcexWaybillNumber wbnNum, JSONArray statusDetailArr,
                         TscJcexWaybillNumber tscJcexWaybillnumber,
                         JSONObject displayDetailJson, List<TscJcexOrderdetail> orderdetailList,
                         List<TscJcexStatusDetail> detailList);
}
