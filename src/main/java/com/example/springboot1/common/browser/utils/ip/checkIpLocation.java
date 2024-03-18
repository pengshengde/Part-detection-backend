package com.example.springboot1.common.browser.utils.ip;

public class checkIpLocation {
    /**
     * 判断一个IP地址是否为内网IP
     * @param ipAddress IP地址
     * @return 是否为内网IP
     */
    public static String isInternalIP(String ipAddress) {
        // IPv4内网地址判断
        if (ipAddress.startsWith("127.") || ipAddress.startsWith("169.254.")) {
            return "内网";
        } else if (ipAddress.startsWith("10.") || ipAddress.startsWith("192.168.")) {
            return "内网";
        } else if (ipAddress.startsWith("172.")) {
            // 判断是否为172.16.0.0 ~ 172.31.255.255之间的地址
            String[] addressParts = ipAddress.split("\\.");
            int secondPart = Integer.parseInt(addressParts[1]);
            if (secondPart >= 16 && secondPart <= 31) {
                return "内网";
            }
        }

        // IPv6内网地址判断
        if (ipAddress.startsWith("fd") || ipAddress.startsWith("fc")) {
            return "内网";
        }

        return "外网";
    }

}
