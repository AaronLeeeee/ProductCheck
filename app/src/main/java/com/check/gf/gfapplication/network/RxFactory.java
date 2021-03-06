package com.check.gf.gfapplication.network;


/**
 * Api Factory
 *
 * @author nEdAy
 */
public class RxFactory {
    private static final Object monitor = new Object();
    private static PublicApi mPublicApi;
    private static UserApi mUserApi;
    private static CheckApi mCheckApi;


    public static PublicApi getPublicServiceInstance() {
        synchronized (monitor) {
            return (mPublicApi == null) ?
                    mPublicApi = new RxService<>(PublicApi.class).getService() : mPublicApi;
        }
    }

    public static UserApi getUserServiceInstance() {
        synchronized (monitor) {
            return (mUserApi == null) ?
                    mUserApi = new RxService<>(UserApi.class).getService() : mUserApi;
        }
    }

    public static CheckApi getCheckServiceInstance() {
        synchronized (monitor) {
            return (mCheckApi == null) ?
                    mCheckApi = new RxService<>(CheckApi.class).getService() : mCheckApi;
        }
    }
}
