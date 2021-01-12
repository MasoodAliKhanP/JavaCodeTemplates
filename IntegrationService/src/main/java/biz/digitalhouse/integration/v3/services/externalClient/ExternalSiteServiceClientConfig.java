package biz.digitalhouse.integration.v3.services.externalClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by vitaliy.babenko
 * on 03.08.2017.
 */
@Configuration
public class ExternalSiteServiceClientConfig {

    private static final Log log = LogFactory.getLog(ExternalSiteServiceClientConfig.class);

    @Bean
    public ExternalSiteServiceClient externalSiteServiceClient() {


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override public void log(String message) {
                log.debug(message);
            }
        });

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient ok = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(30, 300, TimeUnit.SECONDS))
                .addInterceptor(interceptor)
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("dd-MM-yyyy'T'HH:mm:ss'Z'X")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(ok)
                .baseUrl("https://defaulturl.local/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(ExternalSiteServiceClient.class);
    }
}
