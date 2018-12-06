package web.info.LogisticsInfoJiaCheng.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.info.LogisticsInfoJiaCheng.mapper.TscJcexOrderdetailMapper;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexOrderdetail;
import web.info.LogisticsInfoJiaCheng.service.TscJcexOrderdetailService;

@Service
public class TscJcexOrderdetailServiceImpl implements TscJcexOrderdetailService {
    @Autowired
    private TscJcexOrderdetailMapper tscJcexOrderdetailMapper;

    @Override
    public int saveTscJcexOrderdetail(TscJcexOrderdetail tscJcexOrderdetail) {
        return tscJcexOrderdetailMapper.saveTscJcexOrderdetail(tscJcexOrderdetail);
    }
}
