package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FourOperations {
    private static int numberOfExercises;
    private static int range;
    private static final String[] OPERATORS = {"+", "-", "*", "/"};
    private static Set<String> generatedExercises = new HashSet<>();
    private static List<String> answers = new ArrayList<>();

    /**
     * 程序入口
     * @param args
     */
    public static void main(String[] args) {
        parseArguments(args);
        try {
            generateExercises();
            writeToFile("Exercises.txt", generatedExercises);
            writeAnswersToFile("Answers.txt", answers);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * 解析-n,-r
     * @param args
     */
    private static void parseArguments(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Usage: Myapp.exe -n <number> -r <range>");
        }
        for (int i = 0; i < args.length; i++) {
            if ("-n".equals(args[i])) {
                numberOfExercises = Integer.parseInt(args[++i]);
            } else if ("-r".equals(args[i])) {
                range = Integer.parseInt(args[++i]);
            }
        }
        if (numberOfExercises <= 0 || range <= 0) {
            throw new IllegalArgumentException("Both -n and -r must be positive integers.");
        }
    }

    /**
     * 生成题目集合和答案集合
     */
    private static void generateExercises() {
        Random rand = new Random();
        while (generatedExercises.size() < numberOfExercises) {
            String exercise = generateRandomExpression(rand);
            generatedExercises.add(exercise);
            double answer = evaluateExpression(exercise);
            answers.add(String.valueOf(answer));
        }
    }

    /**
     * 生成随机题目
     * @param rand
     * @return
     */
    private static String generateRandomExpression(Random rand) {
        String leftOperand = rand.nextBoolean() ? generateNaturalNumber(rand) : generateProperFraction(rand);
        String operator = OPERATORS[rand.nextInt(OPERATORS.length)];
        String rightOperand = rand.nextBoolean() ? generateNaturalNumber(rand) : generateProperFraction(rand);
        return leftOperand + " " + operator + " " + rightOperand;
    }

    /**
     * 生成自然数
     * @param rand
     * @return
     */
    private static String generateNaturalNumber(Random rand) {
        return String.valueOf(rand.nextInt(range) + 1);
    }

    /**
     * 生成真分数
     * @param rand
     * @return
     */
    private static String generateProperFraction(Random rand) {
        int numerator = rand.nextInt(range) + 1;
        int denominator = rand.nextInt(range - 1) + 1;
        return numerator + "/" + denominator;
    }

    /**
     * 计算题目
     * @param expression
     * @return
     */
    private static double evaluateExpression(String expression) {
        String[] tokens = expression.split(" ");
        double leftOperand = parseOperand(tokens[0]);
        double rightOperand = parseOperand(tokens[2]);
        String operator = tokens[1];

        switch (operator) {
            case "+":
                return leftOperand + rightOperand;
            case "-":
                return leftOperand - rightOperand;
            case "*":
                return leftOperand * rightOperand;
            case "/":
                return leftOperand / rightOperand;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    /**
     * 解析题目
     * @param operand
     * @return
     */
    private static double parseOperand(String operand) {
        if (operand.contains("/")) {
            String[] fractionParts = operand.split("/");
            return Double.parseDouble(fractionParts[0]) / Double.parseDouble(fractionParts[1]);
        }
        return Double.parseDouble(operand);
    }

    /**
     * 把题目集合写入文件
     * @param filename
     * @param exercises
     * @throws IOException
     */
    private static void writeToFile(String filename, Set<String> exercises) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String exercise : exercises) {
                writer.write(exercise);
                writer.newLine();
            }
        }
    }

    /**
     * 把答案集合写入文件
     * @param fileName
     * @param answers
     * @throws IOException
     */
    private static void writeAnswersToFile(String fileName, List<String> answers) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String answer : answers) {
                writer.write(answer);
                writer.newLine();
            }
        }
    }
}
