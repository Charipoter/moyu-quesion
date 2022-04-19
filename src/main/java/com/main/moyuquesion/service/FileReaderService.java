package com.main.moyuquesion.service;

import com.main.moyuquesion.model.Quesion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileReaderService {
    private String path = "src\\main\\resources\\com\\main\\moyuquesion\\files";

    public Map<String, List<Quesion>> makeFileToMap() throws IOException {
        File file = new File(path);
        Pattern pattern = Pattern.compile(".+[（(]\\s*(\\w+)\\s*[）)]");
        Map<String, List<Quesion>> fileMap = new HashMap<>();

        for (String fileName: Objects.requireNonNull(file.list())) {
            BufferedReader fileReader = new BufferedReader(new FileReader(path + "\\" + fileName));
            List<Quesion> quesionList = new ArrayList<>();
            String quesionAnswer = "";
            StringBuilder stringBuilder = new StringBuilder();

            while (fileReader.ready()) {
                String line = fileReader.readLine();

                if (line.equals("")) {
                    if (stringBuilder.length() > 0) {
                        quesionList.add(new Quesion(quesionAnswer, stringBuilder.toString()));
                    }
                    stringBuilder = new StringBuilder();

                    while (fileReader.ready()) {
                        line = fileReader.readLine();
                        if (!line.equals("")) {
                            break;
                        }
                    }

                    if (!fileReader.ready()) {
                        break;
                    }
                }
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    line = line.replaceAll("[（(]\\s*\\w+\\s*[）)]", "()");
                    quesionAnswer = matcher.group(1);
                }

                stringBuilder.append(line).append("\n");
            }
            fileMap.put(fileName, quesionList);
        }
        return fileMap;
    }
}


