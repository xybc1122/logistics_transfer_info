package web.info.LogisticsInfoJiaCheng.provider;

import web.info.LogisticsInfoJiaCheng.pojo.TscJcexOrderdetail;
import web.info.utils.StrUtils;

import java.util.List;
import java.util.Map;

public class TscJcexOrderdetailProvider {


    @SuppressWarnings("unchecked")
    public String saveOrderdetail(Map<String, Object> mapStr) {
        List<TscJcexOrderdetail> orderdetailList = (List<TscJcexOrderdetail>) mapStr.get("orderdetailList");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `tsc_jcex_orderdetail`(`wbn_id`,`child_number`,`length`,`width`,`hight`,`weight`)VALUES");
        for (TscJcexOrderdetail orderdetail : orderdetailList) {
            sb.append("(" + orderdetail.getWbnId());
            sb.append(",");
            StrUtils.appBuider(sb, orderdetail.getChildNumber());
            sb.append(",");
            sb.append(orderdetail.getLength());
            sb.append(",");
            sb.append(orderdetail.getWidth());
            sb.append(",");
            sb.append(orderdetail.getHight());
            sb.append(",");
            sb.append(orderdetail.getWeight());
            sb.append("),");
        }
        String sql = sb.toString().substring(0, sb.length() - 1);
        return sql;
    }

}
