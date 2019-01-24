package web.info.utils;
import org.apache.commons.lang3.StringUtils;

public class StrUtils {
    /**
     * 封装Append
     */
    public static StringBuilder appBuider(StringBuilder sb, String str) {
        if (StringUtils.isEmpty(str)) {
            sb.append(str);
        } else {
            sb.append("'" + str + "'");
        }
        return sb;
    }

}
