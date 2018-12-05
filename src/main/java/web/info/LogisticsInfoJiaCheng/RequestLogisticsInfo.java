package web.info.LogisticsInfoJiaCheng;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import web.info.LogisticsInfoJiaCheng.paramsConfig.ParamsConfig;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexStatusDetail;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexWaybillNumber;
import web.info.LogisticsInfoJiaCheng.service.TscJcexStatusDetailService;
import web.info.LogisticsInfoJiaCheng.service.TscJcexWaybillNumberService;
import web.info.config.BaseRedisService;
import web.info.toos.Constants;
import web.info.utils.DateUtils;
import web.info.utils.HttpUtils;

import java.util.List;

@Component
public class RequestLogisticsInfo {
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
     * redis
     */
    @Autowired
    private BaseRedisService baseRedisService;

    @Scheduled(cron = Constants.TRACK_SIZE)
    public void getTrack() {
        TscJcexWaybillNumber tscJcexWaybillnumber = new TscJcexWaybillNumber();
        TscJcexStatusDetail tscJcexStatusdetail = new TscJcexStatusDetail();
        List<TscJcexWaybillNumber> tscJcexStatus = tscJcexWaybillNumberService.tscJcexStatusNumberList();
        String redisListKey;
        for (TscJcexWaybillNumber wbnNum : tscJcexStatus) {
            String result = HttpUtils.postJcString(Constants.URL_Track, ParamsConfig.trackParamsConfig(wbnNum.getWaybillNumber()), 5000);
            if (StringUtils.isNotEmpty(result)) {
                JSONObject trackJson = JSONObject.parseObject(result);
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
                            //物流轨迹
                            for (int j = 0; j < statusDetailArr.size(); j++) {
                                JSONObject statusDetailArrJson = JSONObject.parseObject(statusDetailArr.get(j).toString());
                                String status = (String) statusDetailArrJson.get("status");
                                //如果状态是交付
                                String statusCnName = (String) statusDetailArrJson.get("statuscnname");
                                String locate = (String) statusDetailArrJson.get("locate");
                                String time = (String) statusDetailArrJson.get("time");
                                //判断是否为空//如果空存入
                                //从redis获取key
                                redisListKey = baseRedisService.getListKey(wbnNum.getWaybillNumber());
                                if (StringUtils.isEmpty(redisListKey)) {
                                    //存入redis
                                    baseRedisService.setList(wbnNum.getWaybillNumber(), statusDetailArr);
                                } else {
                                    JSONArray redisArr = JSONArray.parseArray(redisListKey);
                                    for (int k = 0; k < redisArr.size(); k++) {
                                        JSONObject redisObj = JSONObject.parseObject(redisArr.get(k).toString());
                                        if (redisObj.containsKey("status")) {
                                            String redisStatus = (String) redisObj.get("status");
                                            if (status.equals(redisStatus)) {
                                                return;
                                            }
                                        }
                                    }
                                    //存入数据库对象
                                    tscJcexStatusdetail.setStatusCnName(statusCnName);
                                    tscJcexStatusdetail.setLocate(locate);
                                    tscJcexStatusdetail.setTime(DateUtils.dateGetTime(time));
                                    tscJcexStatusdetail.setStatus(status);
                                    tscJcexStatusdetail.setWbnId(wbnNum.getWbnId());
                                    if (status.equals("Delivered")) {
                                        //如果已送达 更新订单号状态 跳出循环
//                                    tscJcexWaybillNumberService.upTscJcexWaybillNumber(wbnNum.getWbnId());
                                        tscJcexStatusDetailService.saveTscJcexWaybillNumber(tscJcexStatusdetail);
                                        break;
                                    }
                                    tscJcexStatusDetailService.saveTscJcexWaybillNumber(tscJcexStatusdetail);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

