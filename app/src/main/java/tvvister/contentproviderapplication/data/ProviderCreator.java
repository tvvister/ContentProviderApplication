package tvvister.contentproviderapplication.data;

import retrofit.RestAdapter;

public class ProviderCreator {
    private static final String uri = "http://download.cdn.yandex.net/mobilization-2016";

    public static ISingerInfoProvider createSingerInfoProvider()
    {
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(uri).build();

        ISingerInfoProvider provider = restAdapter.create(ISingerInfoProvider.class);
        return provider;
    }
}
