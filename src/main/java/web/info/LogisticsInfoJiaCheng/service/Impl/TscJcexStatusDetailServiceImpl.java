package web.info.LogisticsInfoJiaCheng.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexStatusDetail;
import web.info.LogisticsInfoJiaCheng.mapper.TscJcexStatusDetailMapper;
import web.info.LogisticsInfoJiaCheng.service.TscJcexStatusDetailService;

@Service
public class TscJcexStatusDetailServiceImpl implements TscJcexStatusDetailService {
    @Autowired
    private TscJcexStatusDetailMapper tscJcexStatusDetailMapper;

    @Override
    public int saveTscJcexWaybillNumber(TscJcexStatusDetail tscJcexStatusDetail) {
        return tscJcexStatusDetailMapper.saveTscJcexWaybillNumber(tscJcexStatusDetail);
    }
}
