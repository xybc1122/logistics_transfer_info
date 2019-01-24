package web.info.utils;

import org.apache.commons.lang.StringUtils;
import web.info.LogisticsInfoJiaCheng.pojo.LogisticsData;
import web.info.config.RedisService;

import java.util.concurrent.ConcurrentHashMap;

public class CheckUtils {
    //并发 hashMap
    private static ConcurrentHashMap<String, Long> hashMap = new ConcurrentHashMap<>();

    public static boolean checkAppKey(LogisticsData logistics, RedisService redisService) {
        Long time = hashMap.get(logistics.getAppKey());
        if (time != null) {
            //如果map里的时间大于
            if (logistics.getDateTime() < time + 2 * 60 * 1000) {
                return false;
            }
        }
        String token = redisService.getStirngKey(logistics.getAppKey());
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        if (!token.equals(logistics.getToken())) {
            return false;
        }
        String sign = logistics.getAppKey() + logistics.getDateTime() +
                logistics.getCurrentPage() + logistics.getPageSize();
        String md5Key = MD5Util.MD5(sign);
        if (StringUtils.isNotEmpty(md5Key) && !md5Key.equals(logistics.getMd5Key())) {
            return false;
        }
        hashMap.put(logistics.getAppKey(), logistics.getDateTime());
        return true;
    }
}
