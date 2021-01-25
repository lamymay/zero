package com.arc.zero.config.mybatis.type.handler;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mybatis结果集类型转换为java类型
 *
 * @author yechao
 * @date 2021/01/12 11:01 上午
 */
@Component
//@MappedTypes(JSONObject.class)
//@MappedJdbcTypes(JdbcType.VARCHAR)
public class JSONObjectHandler extends BaseTypeHandler<JSONObject> {

    public JSONObject delResult(String jsonSource) throws SQLException {
        if (jsonSource != null) {
            JSONObject jsonObject;
            try {
                jsonObject = JSONObject.parseObject(jsonSource);
            } catch (JSONException ex) {
                throw new SQLException("There is an error converting JSONObject to json format for the content:" + jsonSource);
            }
            return jsonObject;
        }
        return null;
    }

    /**
     * @param ps
     * @param i
     * @param parameter 需要转换的类型,JSON类型
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JSONObject parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toJSONString());
    }

    @Override
    public JSONObject getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return delResult(rs.getString(columnName));
    }

    @Override
    public JSONObject getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return delResult(rs.getString(columnIndex));
    }

    @Override
    public JSONObject getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return delResult(cs.getString(columnIndex));
    }

}