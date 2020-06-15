package com.lijipeng.search.manager;

import tk.mybatis.mapper.util.StringUtil;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class MySQL {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://111.230.135.149:3306/es?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false";
    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "lijipeng";

    public static void main(String[] args) {
//        createJava("read_book_pd");
        createMapper("read_book_pd");
    }
    public static void createJava(String tableName){
        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "select column_name,column_comment,data_type from information_schema.columns where   table_schema = 'es' and\n" +
                    " table_name = '"+tableName+"' ";
            ResultSet rs = stmt.executeQuery(sql);
            StringBuilder className=new StringBuilder();
            String []arr=tableName.split("_");
            for(int i=0;i<arr.length;i++){
                className.append(arr[i].substring(0,1).toUpperCase()).append(arr[i].substring(1));
            }
            StringBuilder classContent=new StringBuilder();
            classContent.append("package com.lijipeng.search.manager.po;\n\n");
            classContent.append("import java.util.*;\n\n\n");
            classContent.append("import lombok.Getter;\n");
            classContent.append("import lombok.Setter;\n");


            classContent.append("@Setter\n");
            classContent.append("@Getter\n");
            classContent.append("public class "+className+"PO{\n");
            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                String column_name  = rs.getString("column_name");
                String column_comment = rs.getString("column_comment");
                String data_type = rs.getString("data_type");

                StringBuilder field=new StringBuilder();
                field.append("    /**\n");
                field.append("     * "+column_comment+"\n");
                field.append("     */\n");
                field.append("    private ");
                if(data_type.equals("text")||data_type.equals("varchar")){
                    field.append("String");
                }else if(data_type.equals("bigint")){
                    field.append("Long");
                }else if(data_type.equals("int")||data_type.equals("tinyint")){
                    field.append("Integer");
                }else if(data_type.equals("datetime")){
                    field.append("Date");
                }
                field.append(" ");
                String []columns=column_name.split("_");
                for(int i=0;i<columns.length;i++){
                    if(i==0){
                        field.append(columns[i]);
                    }else{
                        field.append(columns[i].substring(0,1).toUpperCase()).append(columns[i].substring(1));
                    }
                }
                field.append(";\n");
                System.out.println(field);
                classContent.append(field);
            }
            classContent.append("}");
            Files.write(Paths.get("E:\\search\\search-planform\\search-manager\\src\\main\\java\\com\\lijipeng\\search\\manager\\po\\"+className+"PO.java"), classContent.toString().getBytes());
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }

    }
    public static void createMapper(String tableName){
        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "select column_name,column_key,column_comment,data_type from information_schema.columns where   table_schema = 'es' and\n" +
                    " table_name = '"+tableName+"' ";
            ResultSet rs = stmt.executeQuery(sql);
            StringBuilder className=new StringBuilder();
            String []arr=tableName.split("_");
            for(int i=0;i<arr.length;i++){
                className.append(arr[i].substring(0,1).toUpperCase()).append(arr[i].substring(1));
            }
            StringBuilder xmlContent=new StringBuilder();
            xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xmlContent.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
            xmlContent.append("<mapper namespace=\"com.lijipeng.search.manager.dao."+className+"Mapper\">\n");
            StringBuilder resultMap=new StringBuilder();
            resultMap.append("  <resultMap id=\""+className+"\" type=\"com.lijipeng.search.manager.po."+className+"PO\">\n");
            StringBuilder insert=new StringBuilder();
            StringBuilder values=new StringBuilder();
            insert.append("  <insert id=\"add"+className+"\">\n");
            insert.append("     insert into "+tableName+"\n     (");
            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                String column_name  = rs.getString("column_name");
                String column_key = rs.getString("column_key");
                String data_type = rs.getString("data_type");
                StringBuilder result=new StringBuilder();
                StringBuilder property=new StringBuilder();
                String []columns=column_name.split("_");
                for(int i=0;i<columns.length;i++){
                    if(i==0){
                        property.append(columns[i]);
                    }else{
                        property.append(columns[i].substring(0,1).toUpperCase()).append(columns[i].substring(1));
                    }
                }
                if(!StringUtil.isEmpty(column_key)&&"PRI".equals(column_key)){
                    result.append("     <id");
                }else{
                    result.append("     <result");
                    insert.append(column_name+",");
                    values.append("#{entity."+property+"},");
                }
                String jdbcType;
                switch (data_type.toUpperCase()){
                    case "BIGINT":
                        jdbcType="BIGINT";
                        break;
                    case "VARCHAR":
                        jdbcType="VARCHAR";
                        break;
                    case "CHAR":
                        jdbcType="CHAR";
                        break;
                    case "DECIMAL":
                        jdbcType="DECIMAL";
                        break;
                    case "DATE":
                        jdbcType="DATE";
                        break;
                    case "TIMESTAMP":
                    case "DATETIME":
                        jdbcType="TIMESTAMP";
                        break;
                    case "TINYINT":
                        jdbcType="TINYINT";
                        break;
                    case "SMALLINT":
                        jdbcType="SMALLINT";
                        break;
                    case "INT":
                        jdbcType="INTEGER";
                        break;
                    case "FLOAT":
                        jdbcType="FLOAT";
                        break;
                    case "DOUBLE":
                        jdbcType="DOUBLE";
                        break;
                    case "TEXT":
                        jdbcType="LONGVARCHAR";
                        break;
                    default:
                        jdbcType="";
                }
                result.append(" column=\""+column_name+"\" jdbcType=\""+jdbcType+"\"");


                result.append(" property=\""+property+"\" /> \n");
                System.out.println(result);
                resultMap.append(result);
            }
            values.deleteCharAt(values.length()-1);
            insert.deleteCharAt(insert.length()-1);
            insert.append(")\n");
            insert.append("     values(\n     ").append(values).append(")");
            resultMap.append("  </resultMap>\n");
            insert.append("\n  </insert>\n");
            xmlContent.append(resultMap);
            xmlContent.append(insert);
            xmlContent.append("</mapper>");
            Files.write(Paths.get("E:\\search\\search-planform\\search-manager\\src\\main\\resources\\mapper\\"+className+"Mapper.xml"), xmlContent.toString().getBytes());
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }

    }
}
