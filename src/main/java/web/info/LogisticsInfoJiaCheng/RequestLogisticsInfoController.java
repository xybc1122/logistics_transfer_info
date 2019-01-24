package web.info.LogisticsInfoJiaCheng;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import web.info.LogisticsInfoJiaCheng.paramsConfig.ParamsConfig;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexOrderdetail;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexStatusDetail;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexWaybillNumber;
import web.info.LogisticsInfoJiaCheng.service.TscJcexOrderdetailService;
import web.info.LogisticsInfoJiaCheng.service.TscJcexStatusDetailService;
import web.info.LogisticsInfoJiaCheng.service.TscJcexWaybillNumberService;
import web.info.toos.Constants;
import web.info.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class RequestLogisticsInfoController {
    /**
     * 快递订单号存放类
     */
    @Autowired
    private TscJcexWaybillNumberService tscJcexWaybillNumberService;

    /**
     * 轨迹查询存放类
     */
    @Autowired
    private TscJcexStatusDetailService tscJcexStatusDetailService;
    /**
     * 子单号Mapper
     */
    @Autowired
    private TscJcexOrderdetailService tscJcexOrderdetailService;
    /**
     * 生产数据
     *
     * @param wbnNum
     * @return
     */
    public String productionData(TscJcexWaybillNumber wbnNum) {
        String result = HttpUtils.postJcString(Constants.URL_Track, ParamsConfig.trackParamsConfig(wbnNum.getWaybillNumber()), 5000);
        return result;
    }
    /**
     * 轨迹请求数据
     */
    @Scheduled(cron = Constants.TRACK_SIZE)
    @Transactional
    public void getTrack() {
        List<TscJcexOrderdetail> orderdetailList = new ArrayList<>();
        List<TscJcexStatusDetail> detailList = new ArrayList<>();
        TscJcexWaybillNumber tscJcexWaybillnumber = new TscJcexWaybillNumber();
        List<TscJcexWaybillNumber> tscJcexStatus = tscJcexWaybillNumberService.tscJcexStatusNumberList();

        //子单号############# 数据处理
        CountDownLatch end = new CountDownLatch(tscJcexStatus.size());
        int p = 0;
        for (TscJcexWaybillNumber wbnNum : tscJcexStatus) {
            System.out.println(++p + " 号选手 ");
            String result = productionData(wbnNum);
            if (StringUtils.isNotEmpty(result)) {
                JSONObject trackJson = JSONObject.parseObject(result);
                //设置id
                tscJcexWaybillnumber.setWbnId(wbnNum.getWbnId());
                //到达国家
                if (trackJson.containsKey("recipientcountry")) {
                    String recipientcountry = (String) trackJson.get("recipientcountry");
                    tscJcexWaybillnumber.setRecipientCountry(recipientcountry);
                }
                if (trackJson.containsKey("displaydetail")) {
                    JSONArray displayDetail = trackJson.getJSONArray("displaydetail");
                    for (int i = 0; i < displayDetail.size(); i++) {
                        JSONObject displayDetailJson = JSONObject.parseObject(displayDetail.get(i).toString());
                        //实重
                        if (displayDetailJson.containsKey("totalweight")) {
                            String totalWeight = (String) displayDetailJson.get("totalweight");
                            tscJcexWaybillnumber.setTotalWeight(Double.parseDouble(totalWeight));
                        }
                        //计费重
                        if (displayDetailJson.containsKey("chargeweight")) {
                            String chargeWeight = (String) displayDetailJson.get("chargeweight");
                            tscJcexWaybillnumber.setChargeWeight(Double.parseDouble(chargeWeight));
                        }
                        //柴积重
                        if (displayDetailJson.containsKey("checkinvolumeweight")) {
                            String checkInVolumeWeight = (String) displayDetailJson.get("checkinvolumeweight");
                            tscJcexWaybillnumber.setCheckInVolumeWeight(Double.parseDouble(checkInVolumeWeight));
                        }
                        if (displayDetailJson.containsKey("statusdetail")) {
                            JSONArray statusDetailArr = displayDetailJson.getJSONArray("statusdetail");
                            try {
                                //消费信息
                                tscJcexWaybillNumberService.consumptionData(wbnNum, statusDetailArr, tscJcexWaybillnumber, displayDetailJson, orderdetailList, detailList);
                            } finally {
                                end.countDown();
                                System.out.println("处理数据完成");
                            }
                        }
                    }
                }
            }
        }
        try {
            end.await();
            System.out.println("先手全部跑完");
            //循环插入List
            if (detailList.size() > 0) {
                tscJcexStatusDetailService.saveTscJcexStatusDetail(detailList);
            }
            if (orderdetailList.size() > 0) {
                tscJcexOrderdetailService.saveTscJcexOrderdetail(orderdetailList);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

