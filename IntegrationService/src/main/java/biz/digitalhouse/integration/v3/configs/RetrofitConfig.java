package biz.digitalhouse.integration.v3.configs;

import biz.digitalhouse.integration.v3.services.bingoGame.bingoServerClient.BingoServerService;
import biz.digitalhouse.integration.v3.services.userservice.UserServiceClient;
import biz.digitalhouse.integration.v3.utils.CalendarSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by vitaliy.babenko
 * on 28.10.2016.
 */
@Configuration
public class RetrofitConfig {

    private final Log log = LogFactory.getLog(getClass());

    @Value("${url.bingoserver.service}")
    private String BINGO_SERVER_ENDPOINT;

    @Value("${url.user.service}")
    private String USER_SERVICE_ENDPOINT;

    @Bean
    public OkHttpClient okHttpClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override public void log(String message) {
                log.debug(message);
            }
        });

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(10, 300, TimeUnit.SECONDS))
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    @Bean
    public BingoServerService bingoServerService() {

        Gson gson = new GsonBuilder()
                .setDateFormat("dd-MM-yyyy'T'HH:mm:ss'Z'X")
                .registerTypeAdapter(Calendar.class, new CalendarSerializer())
                .registerTypeAdapter(GregorianCalendar.class, new CalendarSerializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient())
                .baseUrl(BINGO_SERVER_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(BingoServerService.class);
    }

    @Bean
    public UserServiceClient userServiceClient() {

        Gson gson = new GsonBuilder()
                .setDateFormat("dd-MM-yyyy'T'HH:mm:ss'Z'X")
                .registerTypeAdapter(Calendar.class, new CalendarSerializer())
                .registerTypeAdapter(GregorianCalendar.class, new CalendarSerializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient())
                .baseUrl(USER_SERVICE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(UserServiceClient.class);
    }


}
