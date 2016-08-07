package tvvister.contentproviderapplication.data;

import retrofit.Callback;
import retrofit.http.GET;

///http://download.cdn.yandex.net/mobilization-2016/artists.json
public interface ISingerInfoProvider {

    @GET("/artists.json")      //here is the other url part.best way is to start using /
    public void getAllSingers(Callback<SingerInfo[]> response);     //string user is for passing values from edittext for eg: user=basil2style,google
    //response is the response from the server which is now in the POJO
}
