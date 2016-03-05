package com.volleytest;

import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends Activity {

	
	private TextView textView;
	private ImageView imageView;
	private NetworkImageView imageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tx);
        imageView = (ImageView) findViewById(R.id.imag);
        imageView2 = (NetworkImageView) findViewById(R.id.network_image_view);
      //getStringVolley();
      //getJsonVolley();
      //getImageVolley();
      //loadImageVolley();
        getNetworkImage();
    }
    /*
     * 获得并显示字符串
     */
    public void getStringVolley() {
		RequestQueue mQueue = Volley.newRequestQueue(this);
		StringRequest stringRequest = new StringRequest("http://www.baidu.com", new Response.Listener<String>(
				) {

					@Override
					public void onResponse(String arg0) {
						// TODO Auto-generated method stub
						textView.setText(arg0);
						 Log.d("TAG", arg0);  
					}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				textView.setText(arg0.getMessage());
				Log.e("TAG", arg0.getMessage(), arg0);  
			}
		});
		mQueue.add(stringRequest);
	}
    /*
     * 获得并显示json数据
     */
    public void getJsonVolley() {
		RequestQueue mQueue = Volley.newRequestQueue(this);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://www.weather.com.cn/data/sk/101050701.html", 
				null, new Response.Listener<JSONObject>(
				) {
					
					@Override
					public void onResponse(JSONObject arg0) {
						// TODO Auto-generated method stub
						textView.setText(arg0.toString());
						 Log.d("TAG", arg0.toString());  
					}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				textView.setText(arg0.getMessage());
			}
		});
		mQueue.add(jsonObjectRequest);
	}
    /*
     * 获得并显示图片
     */
    public void getImageVolley() {
		RequestQueue mQueue = Volley.newRequestQueue(this);
		ImageRequest imageRequest = new ImageRequest("http://img1.niutuku.com/hd/1208/1738/img-1738-yqcky50og4i.jpg", 
				new Response.Listener<Bitmap>() {

					@Override
					public void onResponse(Bitmap arg0) {
						// TODO Auto-generated method stub
						imageView.setImageBitmap(arg0);
					}
				}, 0, 0, Config.RGB_565, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						imageView.setImageResource(R.drawable.ic_launcher);
					}
				});
		mQueue.add(imageRequest);
	}
   /*
    * 利用ImageLoader来显示图片
    */
    public void loadImageVolley() {
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());
		ImageListener imageListener = ImageLoader.getImageListener(imageView, R.drawable.ic_launcher, R.drawable.ss);
		imageLoader.get("http://img1.niutuku.com/hd/46/17934.jpg", imageListener);
		
	}
   
    /*
     * 利用NetworkImageView来获取图片并显示
     */
    public void getNetworkImage(){
    	RequestQueue requestQueue = Volley.newRequestQueue(this);
    	imageView2.setDefaultImageResId(R.drawable.ic_launcher);
    	imageView2.setErrorImageResId(R.drawable.ss);
    	ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());
    	imageView2.setImageUrl("http://img1.niutuku.com/hd/18/11627.jpg", imageLoader);
    }
    
    
    public class BitmapCache implements ImageCache{
    	
    	private LruCache<String, Bitmap> lruCache;
    	
      public  BitmapCache() {
		int maxsize = 10*1024*1024;
		
		lruCache = new LruCache<String, Bitmap>(maxsize){
			@Override
    		protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes()*value.getHeight();
    		}
    	};
      }
		@Override
		public Bitmap getBitmap(String arg0) {
			// TODO Auto-generated method stub
			return lruCache.get(arg0);
		}

		@Override
		public void putBitmap(String arg0, Bitmap arg1) {
			// TODO Auto-generated method stub
			lruCache.put(arg0, arg1);
		}
    }
}
