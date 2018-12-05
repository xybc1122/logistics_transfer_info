package web.info.LogisticsInfoJiaCheng.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexStatusDetail;


@Mapper
public interface TscJcexStatusDetailMapper {


    /**
     * 存入轨迹信息
     */
    @Insert("INSERT INTO `logisticsmgt`.`tsc_jcex_statusdetail`(`wbn_id`,`status`, `time`,`locate`, `status_cn_name`)\n" +
            "VALUES (#{wbnId},#{status},#{time},#{locate}, #{statusCnName})")
    int saveTscJcexWaybillNumber(TscJcexStatusDetail tscJcexStatusDetail);

}
