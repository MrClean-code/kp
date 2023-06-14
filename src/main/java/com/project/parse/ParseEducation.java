package com.project.parse;

import com.project.HelloController;
import com.project.entity.Education;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseEducation {
    private static Map<String, String> middleScoreDiplomaMap;
    private static Map<String, String> middleScoreExamMap;
    private static List<Education> educationList = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(ParseEducation.class);

    public List<Education> parse() {

        parseMiddleScoreExamTable();

        parseMiddleScoreDiplomaTable();

        List<Education> list = parseMainTable(middleScoreExamMap, middleScoreDiplomaMap);
        return list;
    }

    private static List<Education> parseMainTable(Map<String, String> middleScoreExamMap, Map<String, String> middleScoreDiplomaMap) {
        Document document;
        try {
            document = Jsoup.connect("https://cchgeu.ru/education/programms/").get();
        } catch (IOException e) {
            logger.error("ошибка при обращении к сайту https://cchgeu.ru/education/programms/");
            throw new RuntimeException(e);
        }

        Element table = document.select("table").first();

        Elements rows = table.select("tr");  // разбиваем нашу таблицу на строки по тегу

        for (int i = 2; i < rows.size(); i++) {
            Element row = rows.get(i); //по номеру индекса получает строку
            Elements cols = row.select("td");// разбиваем полученную строку по тегу  на столбы
            if (cols.size() == 1) {
            } else if (cols.get(1) != null && cols.get(2) != null && cols.get(3) != null) {
                String mas[] = cols.get(1).text().split(" ");
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 1; j < mas.length; j++) {
                    stringBuilder.append(mas[j] + " ");
                }

                String code = mas[0];
                String specialization = String.valueOf(stringBuilder).trim();
                String level = cols.get(2).text();
                String faculty = cols.get(3).text();

                Education education = new Education();

                education.setCode(code);
                education.setSpecialization(specialization);
                education.setLevel(level);
                education.setFaculty(faculty);

                String scoreDiploma = checkEqualsDiploma(education.getCode(), middleScoreDiplomaMap);
                String scoreExam = checkEqualsExam(education.getCode(), middleScoreExamMap);

                education.setMiddleScoreDiploma(scoreDiploma);
                education.setMiddleScoreExam(scoreExam);

                educationList.add(education);
            }
        }
        return educationList;
    }

    private static String checkEqualsExam(String code, Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().equals(code)) {
                return entry.getValue();
            }
        }
        return "нет значения";
    }

    private static String checkEqualsDiploma(String code, Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().equals(code)) {
                return entry.getValue();
            }
        }
        return "нет значения";
    }

    private static Map<String, String> parseMiddleScoreDiplomaTable() {
        Document document;
        middleScoreDiplomaMap = new HashMap<>();

        try {
            document = Jsoup.connect("https://cchgeu.ru/abiturientu/spo/itogi/").get();
        } catch (IOException e) {
            logger.error("ошибка при обращении к сайту https://cchgeu.ru/abiturientu/spo/itogi/");
            throw new RuntimeException(e);
        }

        Element table = document.select("table").first();
        Elements rows = table.select("tr");

        for (int i = 2; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            if (cols.size() == 1) {
            } else if (cols.get(0) != null && cols.get(3) != null) {
                String middleScoreDiplomaKey = cols.get(0).text();
                String middleScoreDiplomaValue = cols.get(3).text();
                middleScoreDiplomaMap.put(middleScoreDiplomaKey, middleScoreDiplomaValue);
            }
        }

//        middleScoreDiplomaMap.entrySet().forEach(entry -> {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        });

        return middleScoreDiplomaMap; // список кодов специальностей и их баллов у спо
    }

    private static Map<String, String> parseMiddleScoreExamTable() {
        Document document;
        middleScoreExamMap = new HashMap<>();

        try {
            document = Jsoup.connect("https://cchgeu.ru/abiturientu/bak-spec/itogi/").get();
        } catch (IOException e) {
            logger.error("ошибка при обращении к сайту https://cchgeu.ru/abiturientu/bak-spec/itogi/");
            throw new RuntimeException(e);
        }

        Element table = document.select("table").get(1);
        Elements rows = table.select("tr");

        for (int i = 2; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            if (cols.size() == 1) {
            } else if (cols.get(0) != null && cols.get(2) != null) {
                String middleScoreDiplomaKey = cols.get(0).text();
                String middleScoreDiplomaValue = cols.get(2).text();
                middleScoreExamMap.put(middleScoreDiplomaKey, middleScoreDiplomaValue);
            }
        }

        Element table2 = document.select("table").get(2);
        Elements rows2 = table2.select("tr");

        for (int i = 2; i < rows2.size(); i++) {
            Element row = rows2.get(i);
            Elements cols = row.select("td");
            if (cols.size() == 1) {
            } else if (cols.get(0) != null && cols.get(2) != null) {
                String middleScoreDiplomaKey = cols.get(0).text();
                String middleScoreDiplomaValue = cols.get(2).text();
                middleScoreExamMap.put(middleScoreDiplomaKey, middleScoreDiplomaValue);
            }
        }

//        middleScoreExamMap.entrySet().forEach(entry -> {
//            System.out.println(entry.getKey() + " " + entry.getValue());}
//        );

        return middleScoreExamMap; // список кодов специальностей и их баллов у бакалавриата и специалитета .
    }
}
