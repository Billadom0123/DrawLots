package com.example.DrawLots.util;

//引入字符串常用工具，返回前端时，可以对String类型进行整理
public class StringUtils
{
        // 判断字符串是否为空
        public static boolean isNullOrEmpty(String str) {
            return str == null || str.trim().isEmpty();
        }

        // 连接多个字符串
        public static String join(String... strings) {
            StringBuilder sb = new StringBuilder();
            for (String str : strings) {
                sb.append(str);
            }
            return sb.toString();
        }

        // 转换字符串为大写
        public static String toUpperCase(String str) {
            return str != null ? str.toUpperCase() : null;
        }

        // 转换字符串为小写
        public static String toLowerCase(String str) {
            return str != null ? str.toLowerCase() : null;
        }

}
