package web.info.LogisticsInfoJiaCheng.mapper;

import org.apache.ibatis.annotations.*;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexStatusDetail;
import web.info.LogisticsInfoJiaCheng.provider.TscJcexStatusDetailProvider;

import java.util.List;


@Mapper
public interface TscJcexStatusDetailMapper {


    /**
     * 存入轨迹信息
     */
    @InsertProvider(type = TscJcexStatusDetailProvider.class,method = "saveDetail")
    int saveTscJcexStatusDetail(@Param("detailList") List<TscJcexStatusDetail> detailList);

    @Select("SELECT\n" +
            "`id`,`wbn_id`,`status`,MAX(`time`)as time,\n" +
            "`locate`,`status_cn_name`\n" +
            "FROM `tsc_jcex_statusdetail`\n" +
            "where wbn_id=#{wbnId}\n" +
            "GROUP BY wbn_id")
    TscJcexStatusDetail getStatusDetail(@Param("wbnId") int wbnId);
}
