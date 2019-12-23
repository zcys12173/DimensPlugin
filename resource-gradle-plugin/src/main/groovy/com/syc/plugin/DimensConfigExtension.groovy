package com.syc.plugin

class DimensConfigExtension {
    List<Integer> matchSizes=[]
    int baseSize = 320

    void matchSizes(List<Integer> matchSizes) {
        this.matchSizes = matchSizes
    }

    void baseSize(int baseSize) {
        this.baseSize = baseSize
    }
}