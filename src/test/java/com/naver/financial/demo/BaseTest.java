package com.naver.financial.demo;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.hamcrest.core.IsNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class BaseTest {
    static final DateTimeFormatter DB_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    private final String api;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BaseTest(String url) {
        this.api = url;
    }

    public JSONObject findUserById(String id) {
        Map<String, Object> resultMap = jdbcTemplate.queryForMap(
                "SELECT * FROM users WHERE id = ?",
                id
        );
        return new JSONObject(resultMap);
    }

    public JSONObject findCouponGroupByCode(String code) {
        Map<String, Object> resultMap = jdbcTemplate.queryForMap(
                "SELECT * FROM coupon_groups WHERE code = ?",
                code
        );
        return new JSONObject(resultMap);
    }

    public JSONObject findCouponByUserIdAndCode(String userId, String code) {
        try {
            Map<String, Object> resultMap = jdbcTemplate.queryForMap(
                    "SELECT * FROM coupons WHERE user_id = ? and code = ?",
                    userId,
                    code
            );
            return new JSONObject(resultMap);
        } catch (Exception e) {
            return null;
        }
    }

    public String getString(JSONObject json, String prop) throws JSONException {
        return json.getString(prop.toUpperCase());
    }

    public int getInt(JSONObject json, String prop) throws JSONException {
        return json.getInt(prop.toUpperCase());
    }

    public String getDateString(JSONObject json, String prop) throws JSONException {
        return LocalDateTime
                .parse(getString(json, prop), DB_TIME_FORMAT)
                .format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public ResultActions failure(ResultActions res, HttpStatus status) throws Exception {
        return res.andExpect(status().is(status.value()))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.status", is(status.value())));
    }

    public ResultActions success(ResultActions res) throws Exception {
        return res.andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response").exists())
                .andExpect(jsonPath("$.error").value(IsNull.nullValue()));
    }

    public String apiUrl(String url) {
        return this.api + "/" + url;
    }

    public ResultActions reqGet(String url) throws Exception {
        return mockMvc.perform(get(apiUrl(url)));
    }

    public ResultActions reqGet(String url, String userId) throws Exception {
        return mockMvc.perform(get(apiUrl(url)).header("X-USER-ID", userId));
    }

    public ResultActions reqPost(String url) throws Exception {
        return mockMvc.perform(post(apiUrl(url)));
    }

    public ResultActions reqPost(String url, JSONObject json) throws Exception {
        return mockMvc.perform(post(apiUrl(url))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()));
    }

    public ResultActions reqPost(String url, String userId) throws Exception {
        return mockMvc.perform(post(apiUrl(url)).header("X-USER-ID", userId));
    }

    public ResultActions reqPost(String url, String userId, JSONObject json) throws Exception {
        return mockMvc.perform(post(apiUrl(url))
                .header("X-USER-ID", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()));
    }

    public ResultActions reqPut(String url) throws Exception {
        return mockMvc.perform(put(apiUrl(url)));
    }

    public ResultActions reqPut(String url, JSONObject json) throws Exception {
        return mockMvc.perform(put(apiUrl(url))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()));
    }

    public ResultActions reqPut(String url, String userId) throws Exception {
        return mockMvc.perform(put(apiUrl(url)).header("X-USER-ID", userId));
    }

    public ResultActions reqPut(String url, String userId, JSONObject json) throws Exception {
        return mockMvc.perform(put(apiUrl(url))
                .header("X-USER-ID", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()));
    }
}
