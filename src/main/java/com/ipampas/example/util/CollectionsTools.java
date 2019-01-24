package com.ipampas.example.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 集合类的公用方法
 *
 * @author fanfan
 * @create 2018/8/22
 * @since
 */
public class CollectionsTools {

    private CollectionsTools() {
    }


    public static <E> boolean isEmpty(Collection<E> collection) {
        return Objects.isNull(collection) || collection.isEmpty();
    }

    public static <E> boolean hasItem(Collection<E> collection) {
        return Objects.nonNull(collection) && !collection.isEmpty();
    }

    /**
     * 字符串拆分，然后根据最大长度进行合并
     *
     * @param text      待拆分字符串
     * @param regex     正则表达式
     * @param maxLength 单个字符串最大长度
     * @return
     */
    public static List<String> splitString(String text, String regex, int maxLength) {
        String[] textArray = text.split(regex);
        ArrayList<String> result = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (; i < textArray.length; i++) {
            if (stringBuilder.length() + textArray[i].length() > maxLength) {
                result.add(stringBuilder.toString());
                stringBuilder.delete(0, maxLength);
            }
            if (i <= textArray.length) {
                stringBuilder.append(textArray[i]);
            }
        }
        if (StringUtils.isNotEmpty(stringBuilder.toString())) {
            result.add(stringBuilder.toString());
        }
        return result;
    }
}
