package web.info.LogisticsInfoJiaCheng.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexStatusDetail;


@Mapper
public interface TscJcexStatusDetailMapper {


    /**
     * 存入轨迹信息
     */
    @Insert("INSERT INTO `logisticsmgt`.`tsc_jcex_statusdetail`(`wbn_id`,`status`, `time`,`locate`, `status_cn_name`)\n" +
            "VALUES (#{wbnId},#{status},#{time},#{locate}, #{statusCnName})")
    int saveTscJcexWaybillNumber(TscJcexStatusDetail tscJcexStatusDetail);

    @Select("SELECT\n" +
            "`id`,`wbn_id`,`status`,MAX(`time`)as time,\n" +
            "`locate`,`status_cn_name`\n" +
            "FROM `tsc_jcex_statusdetail`\n" +
            "where wbn_id=#{wbnId}\n" +
            "GROUP BY wbn_id")
    TscJcexStatusDetail getStatusDetail(@Param("wbnId") int wbnId);
}
