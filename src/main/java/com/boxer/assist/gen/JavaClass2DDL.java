package com.boxer.assist.gen;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;

public class JavaClass2DDL {

    // 类型映射
    private static final Map<Class<?>, String> postgresypeMap = new HashMap<>();
    private static final Map<Class<?>, String> mysqlTypeMap = new HashMap<>();

    static {
        postgresypeMap.put(String.class, "VARCHAR(255)");
        postgresypeMap.put(Long.class, "BIGINT");
        postgresypeMap.put(Integer.class, "INTEGER");
        postgresypeMap.put(Short.class, "SMALLINT");
        postgresypeMap.put(Byte.class, "SMALLINT");
        postgresypeMap.put(Double.class, "NUMERIC(18, 2)");
        postgresypeMap.put(Float.class, "REAL");
        postgresypeMap.put(BigDecimal.class, "DECIMAL(18, 2)");
        postgresypeMap.put(Boolean.class, "BOOLEAN");
        postgresypeMap.put(java.util.Date.class, "TIMESTAMP");
        postgresypeMap.put(java.time.LocalDate.class, "DATE");
        postgresypeMap.put(java.time.LocalDateTime.class, "TIMESTAMP");
        postgresypeMap.put(byte[].class, "BYTEA");
        postgresypeMap.put(char[].class, "CHAR");

        mysqlTypeMap.put(String.class, "VARCHAR(255)");
        mysqlTypeMap.put(Long.class, "BIGINT");
        mysqlTypeMap.put(Integer.class, "INT");
        mysqlTypeMap.put(Short.class, "SMALLINT");
        mysqlTypeMap.put(Byte.class, "TINYINT");
        mysqlTypeMap.put(Double.class, "DOUBLE");
        mysqlTypeMap.put(Float.class, "FLOAT");
        mysqlTypeMap.put(BigDecimal.class, "DECIMAL(18, 2)");
        mysqlTypeMap.put(Boolean.class, "BOOLEAN");
        mysqlTypeMap.put(java.util.Date.class, "DATETIME");
        mysqlTypeMap.put(java.time.LocalDate.class, "DATE");
        mysqlTypeMap.put(java.time.LocalDateTime.class, "DATETIME");
        mysqlTypeMap.put(byte[].class, "BLOB");
        mysqlTypeMap.put(char[].class, "CHAR");
    }

    public static String generateDDL(Class<?> clazz,String dbType) {
        Map<Class<?>, String> typeMappings =null;
        if ("mysql".equalsIgnoreCase(dbType)){
            typeMappings=mysqlTypeMap;
        }else {
            typeMappings=postgresypeMap;
        }
        StringBuilder ddl = new StringBuilder();
        String tableName = convertToSnakeCase(clazz.getSimpleName());
        ddl.append("CREATE TABLE ").append(tableName).append(" (\n");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String columnName = convertToSnakeCase(field.getName());
            String columnType = typeMappings.get(field.getType());
            if (columnType == null) {
                throw new IllegalArgumentException("Unsupported type: " + field.getType());
            }
            ddl.append("    ").append(columnName).append(" ").append(columnType);
            ddl.append(",\n");
        }

        // 删除最后一个逗号
        ddl.setLength(ddl.length() - 2);
        ddl.append("\n);");

        return ddl.toString();
    }

    // 将 camelCase 转换为 snake_case
    private static String convertToSnakeCase(String name) {
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
