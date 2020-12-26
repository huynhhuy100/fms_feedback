package vn.com.r2s.fms.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrotit = null;

    public static Retrofit getClient(String baseURL) {

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseURL);
        builder.addConverterFactory(GsonConverterFactory.create());
        retrotit = builder.build();
        return retrotit;
    }
}
