package com.onesouth.walker.wheredoieat;

import android.os.AsyncTask;

/**
 * Created by Walker on 9/1/2017.
 */

public class DownloadPlaceData extends AsyncTask<String, Void, String> {

    private MapsActivity mapsActivity;

    public DownloadPlaceData(MapsActivity mapsActivity){
        this.mapsActivity = mapsActivity;
    }

    @Override
    protected String doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onPostExecute(String results) {
        if(mapsActivity != null){
            //mapsActivity.parsePlacesData(results);
        }
        detach();
    }

    private void detach(){
        mapsActivity = null;
    }
}
