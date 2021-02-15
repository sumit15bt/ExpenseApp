package com.expense.jwt.api.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

@Data
@NoArgsConstructor
public class Response {

    private  JSONArray merchants;

    public Response(JSONArray data) {
        this.merchants = data;
    }

    public void setData(JSONArray data) {
        this.merchants = data;
    }

    public JSONArray getData() {
        return merchants;
    }

    public String toJsonString() {
        return new JSONArray(this).toString();
    }
}
