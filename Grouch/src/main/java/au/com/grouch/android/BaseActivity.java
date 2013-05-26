package au.com.grouch.android;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by amir on 5/26/13.
 */
public class BaseActivity extends Activity {


    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);

        // Android constructs Activity instances so we must find the ObjectGraph
        // instance and inject this.
        ((GrouchApp) getApplication()).objectGraph().inject(this);
    }



}
