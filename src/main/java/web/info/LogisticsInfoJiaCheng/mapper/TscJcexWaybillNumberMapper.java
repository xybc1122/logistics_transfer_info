package web.info.LogisticsInfoJiaCheng.mapper;

import org.apache.ibatis.annotations.*;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexWaybillNumber;
import web.info.LogisticsInfoJiaCheng.provider.TscJcexWaybillNumberProvider;

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
     * 如果订单已签收 更新单号状态
     * @param wbnId
     * @return
     */
    @Update("UPDATE `tsc_jcex_waybillnumber`\n" +
            "SET `status` = 1\n" +
            "WHERE `wbn_id` = #{wbnId}")
    int upTscJcexWaybillNumberStatus(@Param("wbnId") Long wbnId);

    /**
     * 更新单号表信息
     * @param tscJcexWaybillNumber
     * @return
     */
    @UpdateProvider(type = TscJcexWaybillNumberProvider.class,method = "upTscJcexWaybillNumberInfo")
    int upTscJcexWaybillNumberInfo(TscJcexWaybillNumber tscJcexWaybillNumber);

}
