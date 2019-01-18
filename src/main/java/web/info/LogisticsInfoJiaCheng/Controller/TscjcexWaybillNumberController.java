package web.info.LogisticsInfoJiaCheng.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.info.LogisticsInfoJiaCheng.service.TscJcexWaybillNumberService;
import web.info.config.BaseApiService;
import web.info.config.ResponseBase;

@RestController
@RequestMapping("/api/v1/way")
public class TscjcexWaybillNumberController {
    @Autowired
    private TscJcexWaybillNumberService waybillNumberService;

    /**
     * 获取所有商品列表
     *
     * @return
     */
    @GetMapping("wayList")
    public ResponseBase wayInfo() {
        return BaseApiService.setResultSuccess(waybillNumberService.realTimeInfo());
    }


}
