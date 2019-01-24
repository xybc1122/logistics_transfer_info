package web.info.LogisticsInfoJiaCheng.Controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.info.LogisticsInfoJiaCheng.RequestLogisticsInfoController;
import web.info.LogisticsInfoJiaCheng.pojo.LogisticsData;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexWaybillNumber;
import web.info.LogisticsInfoJiaCheng.service.TscJcexWaybillNumberService;
import web.info.config.BaseApiService;
import web.info.config.RedisService;
import web.info.config.ResponseBase;
import web.info.utils.CheckUtils;
import web.info.utils.PageInfoUtils;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TscjcexWaybillNumberController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private TscJcexWaybillNumberService waybillNumberService;


    /**
     * 获取所有订单列表
     * @return
     */
    @PostMapping("/wayList")
    public ResponseBase wayInfo(@RequestBody LogisticsData logistics) {
        if (CheckUtils.checkAppKey(logistics, redisService)) {
            PageHelper.startPage(logistics.getCurrentPage(), logistics.getPageSize());
            List<TscJcexWaybillNumber> waybillNumberList = waybillNumberService.realTimeInfo(logistics);
            //获得一些信息
            PageInfo<TscJcexWaybillNumber> pageInfo = new PageInfo<>(waybillNumberList);
            Integer currentPage = logistics.getCurrentPage();
            return BaseApiService.setResultSuccess(PageInfoUtils.getPage(pageInfo, currentPage));
        }
        return BaseApiService.setResultError("校验失败/请求时间隔太短");
    }
}
