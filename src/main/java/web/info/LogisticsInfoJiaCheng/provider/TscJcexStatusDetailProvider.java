package web.info.LogisticsInfoJiaCheng.provider;

import org.apache.ibatis.annotations.Insert;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexStatusDetail;
import web.info.utils.StrUtils;

import java.util.List;
import java.util.Map;

public class TscJcexStatusDetailProvider {

    @SuppressWarnings("unchecked")
    public String saveDetail(Map<String, Object> mapStr) {
        List<TscJcexStatusDetail> detailList = (List<TscJcexStatusDetail>) mapStr.get("detailList");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `tsc_jcex_statusdetail`(`wbn_id`,`status`,`time`,`locate`,`status_cn_name`)VALUES");
        for (TscJcexStatusDetail detail : detailList) {
            sb.append("(" + detail.getWbnId());
            sb.append(",");
            StrUtils.appBuider(sb, detail.getStatus());
            sb.append(",");
            sb.append(detail.getTime());
            sb.append(",");
            StrUtils.appBuider(sb, detail.getLocate());
            sb.append(",");
            StrUtils.appBuider(sb, detail.getStatusCnName());
            sb.append("),");
        }
        String sql = sb.toString().substring(0, sb.length() - 1);
        return sql;
    }


}
