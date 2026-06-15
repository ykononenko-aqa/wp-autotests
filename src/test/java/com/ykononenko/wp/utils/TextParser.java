package com.ykononenko.wp.utils;

import java.util.Arrays;
import java.util.List;

public class TextParser {
    public List<String> parseCheckBoxChosenText(String str, String preText) {
        String withoutPrefix = str.replace(preText, "").trim();
        return Arrays.asList(withoutPrefix.split("\\s*,\\s*"));
    }
}
