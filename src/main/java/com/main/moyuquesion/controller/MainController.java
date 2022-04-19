package com.main.moyuquesion.controller;

import com.main.moyuquesion.model.Quesion;
import com.main.moyuquesion.service.CacheService;
import com.main.moyuquesion.service.FileReaderService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainController {

    private final CacheService cacheService = new CacheService();

    private Map<String, List<Quesion>> questions;

    private List<Quesion> currentQuestions;

    private int currentQuestionIndex;

    private final Set<Character> myAnswers = new HashSet<>();

    private final Set<Character> trueAnswers = new HashSet<>();

    private boolean checkIndex(int i) {
        return i >= 0 && i < currentQuestions.size();
    }

    @FXML
    private ListView<?> fileDisplayer;

    @FXML
    private TextArea quesionDisplayer;

    @FXML
    private Label resultLabel;

    @FXML
    private Button aButton;

    @FXML
    private Button bButton;

    @FXML
    private Button cButton;

    @FXML
    private Button dButton;

    @FXML
    private TabPane tabPages;

    void onChangeQuesion() {
        resultLabel.setText("");
        myAnswers.clear();
        trueAnswers.clear();

        for (char c: currentQuestions.get(currentQuestionIndex).getAnswer().toCharArray()) {
            trueAnswers.add(c);
        }

        cacheService.changeCurIndex(currentQuestionIndex);
        cacheService.setCurIsWrong(false);

        quesionDisplayer.setText(currentQuestions.get(currentQuestionIndex).getDescription());

        aButton.setStyle("");
        bButton.setStyle("");
        cButton.setStyle("");
        dButton.setStyle("");
    }

    @FXML
    void goNext(ActionEvent event) {

        if (checkIndex(currentQuestionIndex + 1)) {
            quesionDisplayer.setText(currentQuestions.get(++currentQuestionIndex).getDescription());
        }
        onChangeQuesion();
    }

    @FXML
    void goPre(ActionEvent event) {

        if (checkIndex(currentQuestionIndex - 1)) {
            quesionDisplayer.setText(currentQuestions.get(--currentQuestionIndex).getDescription());
        }
        onChangeQuesion();
    }

    @FXML
    @SuppressWarnings("all")
    void init(ActionEvent event) throws IOException {
        if (fileDisplayer.getItems().size() != 0) {
            return;
        }
        ObservableList list = FXCollections.observableArrayList();

        questions = new FileReaderService().generateQuestions();
        // 错题集也加进去
        list.addAll(questions.keySet());
        list.add("错题(自动添加)");
        fileDisplayer.setItems(list);

        cacheService.initCacheMap(questions.keySet());
    }

    @FXML
    @SuppressWarnings("all")
    void onChangeFile(MouseEvent event) {
        Object fileName = fileDisplayer.getSelectionModel().getSelectedItem();

        if (fileName.equals("错题(自动添加)")) {
            onChangeWrongs();
            return;
        }

        if (!questions.containsKey(fileName)) {
            return;
        }

        currentQuestions = questions.get(fileName);

        cacheService.changeCurFile((String)fileName);

        currentQuestionIndex = cacheService.getCurIndex();

        tabPages.getSelectionModel().select(1);

        onChangeQuesion();
    }

    void onChangeWrongs() {
        currentQuestions = cacheService.getWrongs();

        tabPages.getSelectionModel().select(1);

        if (currentQuestions.size() == 0) {
            quesionDisplayer.setText("你没有错题!");
            return;
        }

        cacheService.changeCurFile("_wrongs");

        currentQuestionIndex = cacheService.getCurIndex();

        quesionDisplayer.setText(currentQuestions.get(currentQuestionIndex).getDescription());

        onChangeQuesion();
    }

    void right(Button button) {
        button.setStyle("-fx-base:rgb(0,255,0,0.3)");
        resultLabel.setText("答对了！！");

        if (cacheService.isCurIsWrong()) {
            return;
        }
        cacheService.deleteWrong(currentQuestions.get(currentQuestionIndex));
    }

    void wrong(Button button) {
        button.setStyle("-fx-base:rgb(255,0,0,0.3)");
        resultLabel.setText("答错了..");

        cacheService.setCurIsWrong(true);
        cacheService.addWrong(currentQuestions.get(currentQuestionIndex));
    }

    void middle(Button button) {
        button.setStyle("-fx-base:rgb(0,0,255,0.3)");
        resultLabel.setText("少选了..");
    }

    boolean checkAnswer(Button button) {
        if (myAnswers.size() < trueAnswers.size()) {
            middle(button);
            return true;
        }

        if (myAnswers.equals(trueAnswers)) {
            right(button);
            return true;
        } else {
            wrong(button);
            return false;
        }
    }

    @FXML
    void selectA(ActionEvent event) {
        myAnswers.add('A');
        if (!checkAnswer(aButton)) {
            myAnswers.remove('A');
        }
    }

    @FXML
    void selectB(ActionEvent event) {
        myAnswers.add('B');
        if (!checkAnswer(bButton)) {
            myAnswers.remove('B');
        }
    }

    @FXML
    void selectC(ActionEvent event) {
        myAnswers.add('C');
        if (!checkAnswer(cButton)) {
            myAnswers.remove('C');
        }
    }

    @FXML
    void selectD(ActionEvent event) {
        myAnswers.add('D');
        if (!checkAnswer(dButton)) {
            myAnswers.remove('D');
        }
    }

}
