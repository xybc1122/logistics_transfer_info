package web.info.LogisticsInfoJiaCheng.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexWaybillNumber;
import web.info.LogisticsInfoJiaCheng.provider.TscJcexWaybillNumberProvider;

import java.util.List;

@Mapper
public interface TscJcexWaybillNumberMapper {
    /**
     * 查询实时物流数据
     *
     * @return
     */
    @Select("SELECT`wbn_id`,`waybill_number`,`recipient_country`,\n" +
            "`status`,`customer_id`,`last_time`\n" +
            "FROM `tsc_jcex_waybillnumber`\n" +
            "WHERE `status` =0")
    @Results({
            @Result(column = "wbn_id", property = "tscJcexStatusDetail",
                    one = @One(select = "web.info.LogisticsInfoJiaCheng.mapper.TscJcexStatusDetailMapper.getStatusDetail",
                            fetchType = FetchType.EAGER))
    })
    List<TscJcexWaybillNumber> realTimeInfo();


    /**
     * 查询订单还没签收的订单信息
     */
    @Select("SELECT`wbn_id`,`waybill_number`,`recipient_country`,`total_weight`,`charge_weight`,`check_in_volume_weight`,`status`,`customer_id`,`last_time`\n" +
            "FROM `tsc_jcex_waybillnumber`\n" +
            "WHERE `status`=0")
    List<TscJcexWaybillNumber> tscJcexStatusNumberList();

    /**
     * 如果订单已签收 更新单号状态
     *
     * @param wbnId
     * @return
     */
    @Update("UPDATE `tsc_jcex_waybillnumber`\n" +
            "SET `status` = 1\n" +
            "WHERE `wbn_id` = #{wbnId}")
    int upTscJcexWaybillNumberStatus(@Param("wbnId") Long wbnId);

    /**
     * 更新单号表信息
     *
     * @param tscJcexWaybillNumber
     * @return
     */
    @UpdateProvider(type = TscJcexWaybillNumberProvider.class, method = "upTscJcexWaybillNumberInfo")
    int upTscJcexWaybillNumberInfo(TscJcexWaybillNumber tscJcexWaybillNumber);

}
