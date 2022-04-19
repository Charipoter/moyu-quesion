package com.main.moyuquesion.service;

import com.main.moyuquesion.model.Quesion;
import java.util.*;

public class CacheService {

    private final Map<String, Integer> cacheMap = new HashMap<>();

    private String curFileName = "";

    private final List<Quesion> wrongs = new ArrayList<>();

    private final Set<Quesion> wrongSet = new HashSet<>();

    private boolean curIsWrong = false;

    public void initCacheMap(Set<String> set) {
        for (String fileName: set) {
            cacheMap.put(fileName, 0);
        }
        cacheMap.put("_wrongs", 0);
    }

    public void changeCurFile(String fileName) {
        curFileName = fileName;
    }

    public void changeCurIndex(int index) {
        cacheMap.put(curFileName, index);
    }

    public int getCurIndex() {
        return cacheMap.get(curFileName);
    }

    public void addWrong(Quesion quesion) {
        if (wrongSet.contains(quesion)) {
            return;
        }
        wrongSet.add(quesion);
        wrongs.add(quesion);
    }

    public void deleteWrong(Quesion quesion) {
        wrongs.remove(quesion);
    }

    public void setCurIsWrong(boolean b) {
        curIsWrong = b;
    }

    public boolean isCurIsWrong() {
        return curIsWrong;
    }

    public List<Quesion> getWrongs() {
        return wrongs;
    }
}
