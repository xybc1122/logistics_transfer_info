package web.info.LogisticsInfoJiaCheng.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexWaybillNumber;

import java.util.List;

@Mapper
public interface TscJcexWaybillNumberMapper {

    /**
     * 查询订单还没签收的订单信息
     */
    @Select("SELECT`wbn_id`,`waybill_number`,`recipient_country`,`total_weight`,`charge_weight`,`check_in_volume_weight`,`status`,`customer_id`,`last_time`\n" +
            "FROM `tsc_jcex_waybillnumber`\n" +
            "WHERE `status`=0")
    List<TscJcexWaybillNumber> tscJcexStatusNumberList();

    /**
     * 如果订单已签收 更新状态
     * @param wbnId
     * @return
     */
    @Update("UPDATE `tsc_jcex_waybillnumber`\n" +
            "SET `status` = 1\n" +
            "WHERE `wbn_id` = #{wbnId}")
    int upTscJcexWaybillNumber(@Param("wbnId") Long wbnId);

}
