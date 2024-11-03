package com.boxer.assist;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.javadoc.Javadoc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaCommentExtractor {
    public static void main(String[] args) throws Exception {
        // 读取 Java 文件
        String path = "src/test/java/com/boxer/assist/OrderTest.java"; // 替换为你的 Java 文件路径
        Path path1 = Paths.get(path);
        System.out.println(path1.toAbsolutePath().toString());
        String code = new String(Files.readAllBytes(path1));

        // 解析代码
        JavaParser parser=new JavaParser();
        CompilationUnit cu = parser.parse(code).getResult().get();

        // 提取类注释
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(cls -> {
            Javadoc javadoc = cls.getJavadoc().orElse(null);
            if (javadoc != null) {
                System.out.println("Class Comment: " + javadoc);
            }
        });

        // 提取字段注释
        cu.findAll(FieldDeclaration.class).forEach(field -> {
            Javadoc javadoc = field.getJavadoc().orElse(null);
            if (javadoc != null) {
                System.out.println("Field Comment: " + javadoc);
            }
        });
    }
}
