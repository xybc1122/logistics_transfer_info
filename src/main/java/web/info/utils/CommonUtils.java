package web.info.utils;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * 常用工具类的封装,md5,uuid
 */
public class CommonUtils {
    /**
     * 封装UUID
     *
     * @return
     */
    public static String generateUUID() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
        return uuid;
    }


}
