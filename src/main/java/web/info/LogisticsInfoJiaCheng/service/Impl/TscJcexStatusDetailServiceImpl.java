package web.info.LogisticsInfoJiaCheng.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexStatusDetail;
import web.info.LogisticsInfoJiaCheng.mapper.TscJcexStatusDetailMapper;
import web.info.LogisticsInfoJiaCheng.service.TscJcexStatusDetailService;

import java.util.List;

@Service
public class TscJcexStatusDetailServiceImpl implements TscJcexStatusDetailService {
    @Autowired
    private TscJcexStatusDetailMapper tscJcexStatusDetailMapper;


    @Override
    public int saveTscJcexStatusDetail(List<TscJcexStatusDetail> tscJcexStatusDetail) {
        return tscJcexStatusDetailMapper.saveTscJcexStatusDetail(tscJcexStatusDetail);
    }
}
