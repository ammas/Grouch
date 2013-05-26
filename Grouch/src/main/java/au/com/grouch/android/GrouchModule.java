package au.com.grouch.android;

import android.content.Context;
import android.location.LocationManager;



import javax.inject.Singleton;

/**
 * Created by amir on 5/26/13.
 */


@dagger.Module(
        injects =  {
                        MainActivity.class }
)

public class GrouchModule {


        private final Context appContext;

        public GrouchModule(Context appContext) {
            this.appContext = appContext;
        }

        @dagger.Provides
        @Singleton
        android.location.LocationManager provideLocationManager() {
            return (LocationManager) appContext.getSystemService(Context.LOCATION_SERVICE);
        }

}
