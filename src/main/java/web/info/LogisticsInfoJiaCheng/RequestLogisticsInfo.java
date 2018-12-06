package web.info.LogisticsInfoJiaCheng;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import web.info.LogisticsInfoJiaCheng.paramsConfig.ParamsConfig;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexOrderdetail;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexStatusDetail;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexWaybillNumber;
import web.info.LogisticsInfoJiaCheng.service.TscJcexOrderdetailService;
import web.info.LogisticsInfoJiaCheng.service.TscJcexStatusDetailService;
import web.info.LogisticsInfoJiaCheng.service.TscJcexWaybillNumberService;
import web.info.config.RedisService;
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
     * 子单号Mapper
     */
    @Autowired
    private TscJcexOrderdetailService tscJcexOrderdetailService;
    /**
     * redis
     */
    @Autowired
    private RedisService baseRedisService;

    /**
     * 轨迹请求数据
     */
    @Scheduled(cron = Constants.TRACK_SIZE)
    @Async("executor")
    @Transactional
    public void getTrack() {
        TscJcexWaybillNumber tscJcexWaybillnumber = new TscJcexWaybillNumber();
        TscJcexStatusDetail tscJcexStatusdetail = new TscJcexStatusDetail();
        TscJcexOrderdetail tscJcexOrderdetail = new TscJcexOrderdetail();
        List<TscJcexWaybillNumber> tscJcexStatus = tscJcexWaybillNumberService.tscJcexStatusNumberList();
        String redisListKey;
        for (TscJcexWaybillNumber wbnNum : tscJcexStatus) {
            String result = HttpUtils.postJcString(Constants.URL_Track, ParamsConfig.trackParamsConfig(wbnNum.getWaybillNumber()), 5000);
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
                            //物流轨迹
                            //从redis获取key
                            redisListKey = baseRedisService.getStirngKey(wbnNum.getWaybillNumber());
                            //判断是否为空//如果空存入
                            if (StringUtils.isEmpty(redisListKey)) {
                                //取得数据 存入数据库
                                for (int j = 0; j < statusDetailArr.size(); j++) {
                                    JSONObject statusDetailArrJson = JSONObject.parseObject(statusDetailArr.get(j).toString());
                                    String status = (String) statusDetailArrJson.get("status");
                                    String statusCnName = (String) statusDetailArrJson.get("statuscnname");
                                    String locate = (String) statusDetailArrJson.get("locate");
                                    String time = (String) statusDetailArrJson.get("time");
                                    //设置对象
                                    tscJcexStatusdetail.setStatusCnName(statusCnName);
                                    tscJcexStatusdetail.setLocate(locate);
                                    tscJcexStatusdetail.setTime(DateUtils.dateGetTime(time));
                                    tscJcexStatusdetail.setStatus(status);
                                    tscJcexStatusdetail.setWbnId(wbnNum.getWbnId());
                                    tscJcexWaybillnumber.setLastTime(DateUtils.dateGetTime(time));
                                    //如果状态是交付
                                    if (status.equals("Delivered")) {
                                        //如果已送达 更新订单号状态
                                        tscJcexWaybillNumberService.upTscJcexWaybillNumberStatus(wbnNum.getWbnId());
                                        tscJcexStatusDetailService.saveTscJcexWaybillNumber(tscJcexStatusdetail);
                                    } else {
                                        //存入数据库
                                        tscJcexStatusDetailService.saveTscJcexWaybillNumber(tscJcexStatusdetail);
                                    }
                                }
                                //更新单号表信息
                                tscJcexWaybillNumberService.upTscJcexWaybillNumberInfo(tscJcexWaybillnumber);
                                //存入redis
                                baseRedisService.setString(wbnNum.getWaybillNumber(), statusDetailArr.toJSONString());
                            } else {
                                //如果缓存里面有数据
                                for (int j = 0; j < statusDetailArr.size(); j++) {
                                    JSONObject statusDetailArrJson = JSONObject.parseObject(statusDetailArr.get(j).toString());
                                    String status = (String) statusDetailArrJson.get("status");
                                    String statusCnName = (String) statusDetailArrJson.get("statuscnname");
                                    String locate = (String) statusDetailArrJson.get("locate");
                                    String time = (String) statusDetailArrJson.get("time");
                                    JSONArray redisArr = JSONArray.parseArray(redisListKey);
                                    boolean flg = false;
                                    //对比两个是否相同
                                    for (int k = 0; k < redisArr.size(); k++) {
                                        JSONObject redisObj = JSONObject.parseObject(redisArr.get(k).toString());
                                        if (redisObj.containsKey("status")) {
                                            String redisStatus = (String) redisObj.get("status");
                                            if (status.equals(redisStatus)) {
                                                flg = true;
                                                break;
                                            }
                                        }
                                    }
                                    //如果有不相同的 存入数据库
                                    if (!flg) {
                                        //存入数据库对象
                                        tscJcexStatusdetail.setStatusCnName(statusCnName);
                                        tscJcexStatusdetail.setLocate(locate);
                                        tscJcexStatusdetail.setTime(DateUtils.dateGetTime(time));
                                        tscJcexStatusdetail.setStatus(status);
                                        tscJcexStatusdetail.setWbnId(wbnNum.getWbnId());
                                        tscJcexWaybillnumber.setLastTime(DateUtils.dateGetTime(time));
                                        //如果状态是交付
                                        if (status.equals("Delivered")) {
                                            //如果已送达 更新订单号状态 已签收
                                            tscJcexWaybillNumberService.upTscJcexWaybillNumberStatus(wbnNum.getWbnId());
                                            tscJcexStatusDetailService.saveTscJcexWaybillNumber(tscJcexStatusdetail);
                                        } else {
                                            tscJcexStatusDetailService.saveTscJcexWaybillNumber(tscJcexStatusdetail);
                                            //更新单号表信息
                                            tscJcexWaybillNumberService.upTscJcexWaybillNumberInfo(tscJcexWaybillnumber);
                                        }
                                    }
                                    //存入redis
                                    baseRedisService.setString(wbnNum.getWaybillNumber(), statusDetailArr.toJSONString());
                                }
                            }
                        }
                        //子单号#############
                        String redisOrderKye = wbnNum.getWaybillNumber() + "-" + wbnNum.getWbnId();
                        String redisOrderArr = baseRedisService.getStirngKey(redisOrderKye);
                        if (StringUtils.isEmpty(redisOrderArr)) {
                            if (displayDetailJson.containsKey("orderdetail")) {
                                JSONArray orderArr = JSONArray.parseArray(displayDetailJson.get("orderdetail").toString());
                                for (int j = 0; j < orderArr.size(); j++) {
                                    JSONObject orderDetailJson = JSONObject.parseObject(orderArr.get(j).toString());
                                    String length = (String) orderDetailJson.get("length");
                                    String width = (String) orderDetailJson.get("width");
                                    String higHt = (String) orderDetailJson.get("hight");
                                    String weight = (String) orderDetailJson.get("weight");
                                    String childNumber = (String) orderDetailJson.get("childnumber");
                                    tscJcexOrderdetail.setLength(Double.parseDouble(length));
                                    tscJcexOrderdetail.setWidth(Double.parseDouble(width));
                                    tscJcexOrderdetail.setHight(Double.parseDouble(higHt));
                                    tscJcexOrderdetail.setWeight(Double.parseDouble(weight));
                                    tscJcexOrderdetail.setChildNumber(childNumber);
                                    tscJcexOrderdetail.setWbnId(wbnNum.getWbnId());
                                    tscJcexOrderdetailService.saveTscJcexOrderdetail(tscJcexOrderdetail);
                                }
                                //存入redis
                                baseRedisService.setString(redisOrderKye, orderArr.toJSONString());
                            }
                        }
                    }
                }
            }
        }
    }
}

