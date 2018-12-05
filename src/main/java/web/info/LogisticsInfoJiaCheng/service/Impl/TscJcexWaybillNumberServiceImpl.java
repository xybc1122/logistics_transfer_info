package web.info.LogisticsInfoJiaCheng.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexWaybillNumber;
import web.info.LogisticsInfoJiaCheng.mapper.TscJcexWaybillNumberMapper;
import web.info.LogisticsInfoJiaCheng.service.TscJcexWaybillNumberService;

import java.util.List;

@Service
public class TscJcexWaybillNumberServiceImpl implements TscJcexWaybillNumberService {
    @Autowired
    private TscJcexWaybillNumberMapper tscJcexWaybillNumberMapper;

    @Override
    public List<TscJcexWaybillNumber> tscJcexStatusNumberList() {
        return tscJcexWaybillNumberMapper.tscJcexStatusNumberList();
    }

    @Override
    public int upTscJcexWaybillNumber(Long wbnId) {
        return tscJcexWaybillNumberMapper.upTscJcexWaybillNumber(wbnId);
    }
}
