package com.common.a.provider;

import com.alibaba.fastjson.JSON;
import com.common.a.dto.AccessToken;
import com.common.a.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubProvider {

    public String getAccessToken(AccessToken accessToken) {

        MediaType json = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        String j = JSON.toJSONString(accessToken);

        RequestBody body = RequestBody.create(json, j);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String result = response.body().string();
            if (request != null) {
                String[] arr = result.split("&");
                if (arr != null) {
                    for (int i = 0; i < arr.length; i++) {
                        String[] s = arr[i].split("=");
                        if (s[0].contains("access_token")) {
                            return s[1];
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public GitHubUser getUser(String token) {

        String url = "https://api.github.com/user/" + token;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String result = response.body().string();
            GitHubUser user = JSON.parseObject(result, GitHubUser.class);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
