package au.com.grouch.android;

import android.app.Application;
import dagger.ObjectGraph;

/**
 * Created by amir on 5/26/13.
 */
public class GrouchApp extends Application {

    private ObjectGraph objectGraph;

    @Override public void onCreate() {
        super.onCreate();

        objectGraph = ObjectGraph.create(new GrouchModule(this));
    }

    public ObjectGraph objectGraph() {
        return objectGraph;
    }
}