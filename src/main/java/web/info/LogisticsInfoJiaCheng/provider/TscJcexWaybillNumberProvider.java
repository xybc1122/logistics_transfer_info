package web.info.LogisticsInfoJiaCheng.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexWaybillNumber;

public class TscJcexWaybillNumberProvider {


    public String upTscJcexWaybillNumberInfo(TscJcexWaybillNumber tNumber) {
        return new SQL() {{
            UPDATE("`tsc_jcex_waybillnumber`");
            if (StringUtils.isNotBlank(tNumber.getRecipientCountry())) {
                SET("`recipient_country` = " + "'"+tNumber.getRecipientCountry()+"'");
            }
            if (tNumber.getTotalWeight() != null) {
                SET("`total_weight` = " + tNumber.getTotalWeight());
            }
            if (tNumber.getChargeWeight() != null) {
                SET("`charge_weight` = " + tNumber.getChargeWeight());
            }
            if (tNumber.getCheckInVolumeWeight() != null) {
                SET("`check_in_volume_weight` = " + tNumber.getCheckInVolumeWeight());
            }
            if (tNumber.getCustomerId() != null) {
                SET("`customer_id` = " + tNumber.getCustomerId());
            }
            if (tNumber.getLastTime() != null) {
                SET("`last_time` = " + tNumber.getLastTime());
            }
            WHERE("wbn_id=" + tNumber.getWbnId());
        }}.toString();
    }
}
