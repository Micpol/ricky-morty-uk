// Generated by Dagger (https://dagger.dev).
package com.uk.androidrecruitmentapp.domain.source;

import com.uk.androidrecruitmentapp.data.network.ApiService;
import com.uk.androidrecruitmentapp.data.network.NetworkDataSource;
import com.uk.androidrecruitmentapp.data.util.RequestExecutor;

import javax.inject.Provider;

import dagger.internal.Factory;

@SuppressWarnings({
        "unchecked",
        "rawtypes"
})
public final class NetworkDataSource_Factory implements Factory<NetworkDataSource> {
    private final Provider<RequestExecutor> requestExecutorProvider;

    private final Provider<ApiService> apiServiceProvider;

    public NetworkDataSource_Factory(Provider<RequestExecutor> requestExecutorProvider,
                                     Provider<ApiService> apiServiceProvider) {
        this.requestExecutorProvider = requestExecutorProvider;
        this.apiServiceProvider = apiServiceProvider;
    }

    public static NetworkDataSource_Factory create(Provider<RequestExecutor> requestExecutorProvider,
                                                   Provider<ApiService> apiServiceProvider) {
        return new NetworkDataSource_Factory(requestExecutorProvider, apiServiceProvider);
    }

    public static NetworkDataSource newInstance(RequestExecutor requestExecutor,
                                                ApiService apiService) {
        return new NetworkDataSource(requestExecutor, apiService);
    }

    @Override
    public NetworkDataSource get() {
        return newInstance(requestExecutorProvider.get(), apiServiceProvider.get());
    }
}
