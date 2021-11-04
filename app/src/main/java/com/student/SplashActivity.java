package com.student;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends Activity
{
	private TextView tvName;
	private ImageView img;

	private FirebaseAuth auth;
	private FirebaseUser user;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		img = findViewById(R.id.icon_splash);
		tvName = findViewById(R.id.txt_name_splash);
		tvName.setTypeface(Typeface.createFromAsset(getAssets(), "Poppins-ExtraBold.ttf"));
		tvName.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up));
		img.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_in));
		auth = FirebaseAuth.getInstance();
		user = auth.getCurrentUser();

		new Handler().postDelayed(new Runnable(){

				@Override
				public void run()
				{
					Intent in;
					if (user != null)
					{
						in = new Intent(SplashActivity.this, MainActivity.class);
					}else{
						in = new Intent(SplashActivity.this, AuthActivity.class);
					}
					in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(in);
				}

			}, 1500);
	}
}
