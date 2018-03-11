package com.app.cleartv.network_protocol;

import com.app.cleartv.applications.MyApplication;
import com.app.cleartv.utils.UrlHelper;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {

    private static final String CACHE_CONTROL = "Cache-Control";

    private static Retrofit retrofit = null;
    private static RetrofitSingleton retrofitSingleton = null;
    private static ApiCalls apiCalls = null;

//    private static RetrofitSingleton ourInstance = new RetrofitSingleton();
//
//    public static RetrofitSingleton getInstance() {
//        return ourInstance;
//    }

//    final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Response originalResponse = chain.proceed(chain.request());
//            return originalResponse.newBuilder()
//                    .header("Cache-Control", String.format("max-age=%d, only-if-cached, max-stale=%d", 120, 0))
//                    .build();
//        }
//    };

    private RetrofitSingleton() {

//        File httpCacheDirectory = new File(Environment.getDownloadCacheDirectory(), "responses");
//        int cacheSize = 10 * 1024 * 1024; // 10 MiB
//        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        MyApplication.getInstance().getExternalCacheDir();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
////                .addInterceptor(interceptor)
//                .addInterceptor(provideHttpLoggingInterceptor())
//                .addInterceptor(provideOfflineCacheInterceptor())
//                .addNetworkInterceptor(provideCacheInterceptor())
//                .connectTimeout(20, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();

//        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new ACFAdapterFactory()).create();
        retrofit = new Retrofit.Builder().
                baseUrl(UrlHelper.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        apiCalls = retrofit.create(ApiCalls.class);

    }

    public static ApiCalls getApiCalls() {
        if (retrofitSingleton == null) {
            retrofitSingleton = new RetrofitSingleton();
        }
        return apiCalls;
    }

    public static Retrofit getRetrofit() {
        if (retrofitSingleton == null) {
            retrofitSingleton = new RetrofitSingleton();
        }
        return retrofit;
    }

//
//    private static Cache provideCache ()
//    {
//        Cache cache = null;
//        try
//        {
//            cache = new Cache( new File( MyApplication.getInstance().getCacheDir(), "http-cache" ),
//                    10 * 1024 * 1024 ); // 10 MB
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
////            Timber.e( e, "Could not create Cache!" );
//        }
//        return cache;
//    }
//
//    private static HttpLoggingInterceptor provideHttpLoggingInterceptor ()
//    {
//        HttpLoggingInterceptor httpLoggingInterceptor =
//                new HttpLoggingInterceptor( new HttpLoggingInterceptor.Logger()
//                {
//                    @Override
//                    public void log (String message)
//                    {
//                        Log.d("Message", message);
////                        Timber.d( message );
//                    }
//                } );
//        httpLoggingInterceptor.setLevel( BuildConfig.DEBUG ? HEADERS : NONE );
//        return httpLoggingInterceptor;
//    }
//
//    public static Interceptor provideCacheInterceptor ()
//    {
//        return new Interceptor()
//        {
//            @Override
//            public Response intercept (Chain chain) throws IOException
//            {
//                Response response = chain.proceed( chain.request() );
//
//                // re-write response header to force use of cache
//                CacheControl cacheControl = new CacheControl.Builder()
//                        .maxAge(2, TimeUnit.MINUTES )
//                        .build();
//
//                return response.newBuilder()
//                        .header( CACHE_CONTROL, cacheControl.toString() )
//                        .build();
//            }
//        };
//    }
//
//    public static Interceptor provideOfflineCacheInterceptor ()
//    {
//        return new Interceptor()
//        {
//            @Override
//            public Response intercept (Chain chain) throws IOException
//            {
//                Request request = chain.request();
//
//                if ( !MyApplication.hasNetwork() )
//                {
//                    CacheControl cacheControl = new CacheControl.Builder()
//                            .maxStale(1, TimeUnit.HOURS )
//                            .build();
//
//                    request = request.newBuilder()
//                            .cacheControl( cacheControl )
//                            .build();
//                }
//                return chain.proceed( request );
//            }
//        };
//    }


}