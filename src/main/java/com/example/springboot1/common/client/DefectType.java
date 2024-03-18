package com.example.springboot1.common.client;

public enum DefectType {
    HUASHANG("huashang", "划伤"),
    JIXIELIU("jixieliu",  "积屑瘤"),
    HUAHEN("huahen", "划痕"),
    FUSHI("fushi", "腐蚀"),
    ZHENDAOWEN("zhendaowen", "振刀纹"),
    GUOQIE("guoqie","过切"),
    ZASE("zase", "杂色");


    private final String pinyinDefect;
    private final String chineseDefect;

    DefectType(String pinyinDefect, String chineseDefect) {
        this.pinyinDefect = pinyinDefect;
        this.chineseDefect = chineseDefect;
    }

    public String getPinyinDefect() {
        return pinyinDefect;
    }

    public String getChineseDefect() {
        return chineseDefect;
    }
}
