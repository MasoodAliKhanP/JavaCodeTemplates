package biz.digitalhouse.integration.v3.managers;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import biz.digitalhouse.integration.v3.dao.GeneralSettingDAO;
import biz.digitalhouse.integration.v3.services.externalClient.AuthClient;
import biz.digitalhouse.integration.v3.services.externalClient.DataClient;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vitaliy.babenko
 * on 13.03.2017.
 */
@Service
public class ClientManagerImpl implements ClientManager {

    private final Log log = LogFactory.getLog(getClass());
    private GeneralSettingDAO generalSettingsDAO;

    private Gson gson = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy'T'HH:mm:ss'Z'X")
            .create();

    private HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            if (log.isDebugEnabled()) {
                log.debug(message);
            }
        }
    });

    @Autowired
    public ClientManagerImpl(GeneralSettingDAO generalSettingsDAO) {
        this.generalSettingsDAO = generalSettingsDAO;
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Override
    @Cacheable("authClientCache")
    public AuthClient getAuthClient(Long brandID) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(5, 300, TimeUnit.SECONDS))
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(generalSettingsDAO.getGeneralSettings(GeneralSettingDAO.KEY_AUTH_URL, brandID))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(AuthClient.class);
    }

    @Override
    @Cacheable("dataClientCache")
    public DataClient getDataClient(Long brandID) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(5, 300, TimeUnit.SECONDS))
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        log.debug("Scratch Card Win url" + 
        			generalSettingsDAO.getGeneralSettings(GeneralSettingDAO.KEY_SCRATCH_CARD_WIN_URL, brandID));
        
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(generalSettingsDAO.getGeneralSettings(GeneralSettingDAO.KEY_SCRATCH_CARD_WIN_URL, brandID))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(DataClient.class);
    }
}
