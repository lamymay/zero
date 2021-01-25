//package com.arc.zero.config.mybatis.type.handler;
//
//import com.alibaba.fastjson.JSONObject;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.ibatis.type.BaseTypeHandler;
//import org.apache.ibatis.type.JdbcType;
//import org.apache.ibatis.type.MappedJdbcTypes;
//import org.apache.ibatis.type.MappedTypes;
//
//import java.sql.CallableStatement;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
///**
// * mybatis的json数据转换类
// *
// * @author yechao
// * @date 2021/1/12 4:48 下午
// */
//
//@MappedTypes(value = {JSONObject.class})
//@MappedJdbcTypes(value = {JdbcType.VARCHAR}, includeNullJdbcType = true)
//public class JsonTypeHandler2<T extends Object> extends BaseTypeHandler<T> {
//
//    private static final ObjectMapper mapper = new ObjectMapper();
//    private Class<T> clazz;
//
//    public JsonTypeHandler2(Class<T> clazz) {
//        if (clazz == null) {
//            throw new IllegalArgumentException("Type argument cannot be null");
//        }
//        this.clazz = clazz;
//    }
//
//    @Override
//    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
//        ps.setString(i, this.toJson(parameter));
//    }
//
//    @Override
//    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
//        return this.toObject(rs.getString(columnName), clazz);
//    }
//
//    @Override
//    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
//        return this.toObject(rs.getString(columnIndex), clazz);
//    }
//
//    @Override
//    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
//        return this.toObject(cs.getString(columnIndex), clazz);
//    }
//
//    private String toJson(T object) {
//        try {
//            return mapper.writeValueAsString(object);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private T toObject(String content, Class<?> clazz) {
//        if (content != null && !content.isEmpty()) {
//            try {
//                return (T) mapper.readValue(content, clazz);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        } else {
//            return null;
//        }
//    }
//
//    static {
//        mapper.configure(Feature.WRITE_NULL_MAP_VALUES, false);
//        mapper.setSerializationInclusion(Inclusion.NON_NULL);
//    }
//}



