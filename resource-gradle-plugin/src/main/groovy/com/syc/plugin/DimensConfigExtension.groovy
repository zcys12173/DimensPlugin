package com.syc.plugin

class DimensConfigExtension {
    List<Integer> matchSizes=[]
    int baseSize = 0

    void matchConfig(List<Integer> matchSizes) {
        this.matchSizes = matchSizes
    }

    void baseConfig(int baseSize) {
        this.baseSize = baseSize
    }
}