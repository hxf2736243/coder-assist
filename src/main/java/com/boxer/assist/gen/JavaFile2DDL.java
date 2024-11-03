package com.boxer.assist.gen;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.javadoc.Javadoc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.File;

public class JavaFile2DDL {

    public static void main(String[] args) throws Exception {
        String path = "src/test/java/com/boxer/assist/OrderTest.java"; // 替换为你的 Java 文件路径
        String ddl = generateDDL(path); // 替换为你想要的表名和数据库类型
        System.out.println(ddl);
    }

    public static String generateDDL(String javaFilePath) throws Exception {
        String code = new String(Files.readAllBytes(Paths.get(javaFilePath)));

        JavaParser javaParser = new JavaParser();
        CompilationUnit cu = javaParser.parse(code).getResult().orElseThrow(() -> new RuntimeException("无法解析 Java 文件"));

        String tableName = getTableName(javaFilePath); // 根据文件名获取表名
        StringBuilder ddlBuilder = new StringBuilder();
        ddlBuilder.append("CREATE TABLE ").append(toUnderscore(tableName)).append(" (\n");

        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(cls -> {
            List<FieldDeclaration> fields = cls.getFields();

            fields.forEach(field -> {
                field.getVariables().forEach(variable -> {
                    String fieldName = toUnderscore(variable.getNameAsString());
                    String dataType = mapJavaTypeToSQLType(field.getElementType().asString());
                    ddlBuilder.append("    ").append(fieldName).append(" ").append(dataType);
                    ddlBuilder.append(",\n");
                });
            });


        });

        if (ddlBuilder.length() > 2) {
            ddlBuilder.setLength(ddlBuilder.length() - 2);
        }
        ddlBuilder.append("\n);");

        // Add comments for each column
        cu.findAll(FieldDeclaration.class).forEach(field -> {
            String columnName = convertToSnakeCase(field.getVariable(0).getNameAsString());
            String comment = getFieldComment(field);
            if (comment != null) {
                ddlBuilder.append("\n COMMENT ON COLUMN ").append(tableName).append(".").append(columnName)
                        .append(" IS '").append(comment).append("';");
            }
        });
        return ddlBuilder.toString();
    }

    private static String mapJavaTypeToSQLType(String javaType) {
        switch (javaType) {
            case "String":
                return "TEXT";
            case "int":
            case "Integer":
                return "INTEGER";
            case "long":
            case "Long":
                return "BIGINT";
            case "double":
            case "Double":
            case "java.math.BigDecimal":
                return "DECIMAL(18, 2)";
            case "float":
            case "Float":
                return "FLOAT";
            case "boolean":
            case "Boolean":
                return "BOOLEAN";
            case "java.util.Date":
            case "java.time.LocalDate":
                return "DATE";
            case "java.time.LocalDateTime":
                return "TIMESTAMP";

            default:
                return "VARCHAR(255)"; // 默认类型
        }
    }

    private static String convertToSnakeCase(String input) {
        return input.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    private static String getTableName(String javaFilePath) {
        // 获取文件名（不包括扩展名）作为表名
        String fileName = Paths.get(javaFilePath).getFileName().toString();
        String table = fileName.substring(0, fileName.lastIndexOf('.'));
        return toUnderscore(table);
    }

    private static String getClassName(String filePath) {
        return new File(filePath).getName().replace(".java", "");
    }

    private static String getFieldComment(FieldDeclaration field) {
        return field.getJavadoc().map(Javadoc::toText).orElse("").trim(); // Replace with actual comment extraction logic
    }

    private static String toUnderscore(String name) {
        StringBuilder result = new StringBuilder();
        for (char c : name.toCharArray()) {
            if (Character.isUpperCase(c) && result.length() > 0) {
                result.append('_');
            }
            result.append(Character.toLowerCase(c));
        }
        return result.toString();
    }
}
