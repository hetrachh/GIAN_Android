package org.gian.gian;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingletone {


    private static MySingletone minstance;
    private RequestQueue requestQueue;
    private static Context mCntx;

    private MySingletone(Context context)
    {
        mCntx = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue()
    {
        if (requestQueue==null)
        {
            requestQueue = Volley.newRequestQueue(mCntx.getApplicationContext());

        }
        return  requestQueue;
    }
    public static synchronized MySingletone getInstance (Context context)
    {
        if(minstance == null)
        {
            minstance = new MySingletone(context);
        }
        return minstance;
    }
        public<T> void addToRequestQueue(Request<T> request)
        {
            getRequestQueue().add(request);

        }
}
