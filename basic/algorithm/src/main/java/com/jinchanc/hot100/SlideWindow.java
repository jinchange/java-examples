package com.jinchanc.hot100;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 滑动窗口分类
 */
public class SlideWindow {

    // 找到字符串中所有字母异位词
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if (s == null || s.isEmpty() || p == null || p.isEmpty() || s.length() < p.length()) {
            return result;
        }

        Map<Character, Integer> pMap = new HashMap<>();
        for (char c : p.toCharArray()) {
            pMap.put(c, pMap.getOrDefault(c, 0) + 1);
        }

        int left = 0, right = 0;
        Map<Character, Integer> m = new HashMap<>();
        while(right < s.length()) {
            char cRight = s.charAt(right);
            char cLeft = s.charAt(left);
            m.put(cRight, m.getOrDefault(cRight, 0) + 1);
            right++;

            // TODO 这里不能用map.size() 相等来判断，因为map.size 是键值对的数量，但k-v，v的值会为2
            if (right - left == p.length()) {
                if (m.equals(pMap)) {
                    result.add(left);
                }
                // TODO 注意，不存在了要删除，否则equals不会相等
                if (m.get(cLeft) == 1) {
                    m.remove(cLeft);
                } else {
                    m.put(cLeft, m.get(cLeft) - 1);
                }
                left ++;
            }
        }
        return result;
    }
    public static void main(String[] args) {
        Assert.isTrue(new SlideWindow().findAnagrams("abcba", "abc").equals(List.of(0, 2)), "error");
    }
}
