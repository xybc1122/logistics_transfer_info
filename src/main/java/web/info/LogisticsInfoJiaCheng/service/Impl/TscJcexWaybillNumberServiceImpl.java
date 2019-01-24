package web.info.LogisticsInfoJiaCheng.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import web.info.LogisticsInfoJiaCheng.pojo.LogisticsData;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexOrderdetail;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexStatusDetail;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexWaybillNumber;
import web.info.LogisticsInfoJiaCheng.mapper.TscJcexWaybillNumberMapper;
import web.info.LogisticsInfoJiaCheng.service.TscJcexWaybillNumberService;
import web.info.config.RedisService;
import web.info.utils.DateUtils;

import java.util.List;

@Service
public class TscJcexWaybillNumberServiceImpl implements TscJcexWaybillNumberService {
    @Autowired
    private TscJcexWaybillNumberMapper tscJcexWaybillNumberMapper;
    /**
     * redis
     */
    @Autowired
    private RedisService baseRedisService;


    @Override
    public List<TscJcexWaybillNumber> realTimeInfo(LogisticsData logistics) {
        return tscJcexWaybillNumberMapper.realTimeInfo(logistics);
    }

    @Override
    public List<TscJcexWaybillNumber> tscJcexStatusNumberList() {
        return tscJcexWaybillNumberMapper.tscJcexStatusNumberList();
    }

    @Override
    public int upTscJcexWaybillNumberStatus(Long wbnId) {
        return tscJcexWaybillNumberMapper.upTscJcexWaybillNumberStatus(wbnId);
    }

    @Override
    public int upTscJcexWaybillNumberInfo(TscJcexWaybillNumber tscJcexWaybillNumber) {
        return tscJcexWaybillNumberMapper.upTscJcexWaybillNumberInfo(tscJcexWaybillNumber);
    }

    @Override
    @Async("executor")
    public void consumptionData(TscJcexWaybillNumber wbnNum, JSONArray statusDetailArr, TscJcexWaybillNumber tscJcexWaybillnumber, JSONObject displayDetailJson,
                                List<TscJcexOrderdetail> orderdetailList, List<TscJcexStatusDetail> detailList
                              ) {
        TscJcexStatusDetail tscJcexStatusdetail;
        String redisListKey;
        //物流轨迹
        //从redis获取key
        redisListKey = baseRedisService.getStirngKey(wbnNum.getWaybillNumber() + "-jc");
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
                tscJcexStatusdetail = tscJcexStatusDetail(statusCnName, locate, DateUtils.dateGetTime(time), status, wbnNum.getWbnId());
                tscJcexWaybillnumber.setLastTime(DateUtils.dateGetTime(time));
                //如果状态是交付
                if (status.equals("Delivered")) {
                    //如果已送达 更新订单号状态
                    upTscJcexWaybillNumberStatus(wbnNum.getWbnId());
                }
                //存入轨迹List
                detailList.add(tscJcexStatusdetail);
            }
            //更新单号表信息
             upTscJcexWaybillNumberInfo(tscJcexWaybillnumber);
            //存入redis
            baseRedisService.setString(wbnNum.getWaybillNumber() + "-jc", statusDetailArr.toJSONString());
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
                    tscJcexStatusdetail = tscJcexStatusDetail(statusCnName, locate, DateUtils.dateGetTime(time), status, wbnNum.getWbnId());
                    tscJcexWaybillnumber.setLastTime(DateUtils.dateGetTime(time));
                    //如果状态是交付
                    if (status.equals("Delivered")) {
                        //如果已送达 更新订单号状态 已签收
                      upTscJcexWaybillNumberStatus(wbnNum.getWbnId());
                    }
                    //存入轨迹List
                    detailList.add(tscJcexStatusdetail);
                    //更新单号表信息
                    upTscJcexWaybillNumberInfo(tscJcexWaybillnumber);

                }
                //存入redis
                baseRedisService.setString(wbnNum.getWaybillNumber() + "-jc", statusDetailArr.toJSONString());
            }
        }
        String redisOrderKye = wbnNum.getWaybillNumber() + "-" + wbnNum.getWbnId() + "-jc";
        String redisOrderArr = baseRedisService.getStirngKey(redisOrderKye);
        if (StringUtils.isEmpty(redisOrderArr)) {
            if (displayDetailJson.containsKey("orderdetail")) {
                JSONArray orderArr = JSONArray.parseArray(displayDetailJson.get("orderdetail").toString());
                for (int j = 0; j < orderArr.size(); j++) {
                    TscJcexOrderdetail tscJcexOrderdetail = new TscJcexOrderdetail();
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
                    //存入List
                    orderdetailList.add(tscJcexOrderdetail);
                }
                //存入redis
                baseRedisService.setString(redisOrderKye, orderArr.toJSONString());
            }
        }
    }

    /**
     * 轨迹对象存入
     *
     * @param statusCnName
     * @param locate
     * @param time
     * @param status
     * @param wId
     * @return
     */
    public TscJcexStatusDetail tscJcexStatusDetail(String statusCnName, String locate, Long time, String status, Long wId) {
        return new TscJcexStatusDetail(status, time, locate, statusCnName, wId);
    }
}
