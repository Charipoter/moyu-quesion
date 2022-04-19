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

    private static final String path = "src\\main\\resources\\com\\main\\moyuquesion\\files";

    public Map<String, List<Quesion>> generateQuestions() throws IOException {
        File file = new File(path);
        Pattern pattern = Pattern.compile(".+[（(]\\s*(\\w+)\\s*[）)]");
        Map<String, List<Quesion>> questions = new HashMap<>();

        for (String fileName: Objects.requireNonNull(file.list())) {
            BufferedReader reader = new BufferedReader(new FileReader(path + "\\" + fileName));
            List<Quesion> currentQuestions = new ArrayList<>();
            String answer = "";
            StringBuilder descriptionBuilder = new StringBuilder();
            while (reader.ready()) {
                String line = reader.readLine();
                if (line.equals("")) {
                    if (descriptionBuilder.length() > 0) {
                        currentQuestions.add(new Quesion(answer, descriptionBuilder.toString()));
                    }
                    descriptionBuilder.setLength(0);

                    while (reader.ready() && !(line = reader.readLine()).equals(""));

                    if (!reader.ready()) break;
                }
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    line = line.replaceAll("[（(]\\s*\\w+\\s*[）)]", "");
                    answer = matcher.group(1);
                }
                descriptionBuilder.append(line).append("\n");
            }
            questions.put(fileName, currentQuestions);
        }
        return questions;
    }
}


