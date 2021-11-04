package com.student;

import android.app.*;
import android.os.*;
import me.anwarshahriar.calligrapher.*;
import android.graphics.*;
import android.widget.*;
import android.view.animation.*;
import android.view.*;
import android.util.*;
import com.google.firebase.auth.*;
import com.google.android.gms.tasks.*;
import android.content.*;

public class AuthActivity extends Activity
{
	private Calligrapher call;
	private Typeface tpb;
	private AlertDialog pdialog;
	
	private TextView tvName;
	private LinearLayout panForm;
	private EditText etMail,etPass;
	private Button btnAuth;
	
	private FirebaseAuth auth;
	private FirebaseUser user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);
		tvName=findViewById(R.id.txt_name_auth);
		panForm=findViewById(R.id.pan_authform);
		etMail=findViewById(R.id.et_mail);
		etPass=findViewById(R.id.et_pass);
		btnAuth=findViewById(R.id.btn_auth);
		
		panForm.startAnimation(AnimationUtils.loadAnimation(this,R.anim.zoom_in));
		call=new Calligrapher(this);
		call.setFont(this,"Poppins-Regular.ttf",true);
		tpb=Typeface.createFromAsset(getAssets(),"Poppins-Bold.ttf");
		tvName.setTypeface(tpb);
		
		auth=FirebaseAuth.getInstance();
		user=auth.getCurrentUser();
		
		btnAuth.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					if(!Patterns.EMAIL_ADDRESS.matcher(s(etMail)+"@gmail.com").matches()||s(etMail).matches("")){
						etMail.setError("Invalid");
					}else if(s(etPass).matches("")){
						etPass.setError("Invalid");
					}else{
						if(user==null){
							showLoading();
							auth.signInWithEmailAndPassword(s(etMail)+"@gmail.com", s(etPass)).addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>(){

									@Override
									public void onComplete(Task<AuthResult> p1)
									{
										if(p1.isSuccessful()){
//											DatabaseReference db=FirebaseDatabase.getInstance().getReference("student");
//											db.child("users/"+auth.getCurrentUser().getUid()+"/name").setValue("Karthik");
											Intent in=new Intent(AuthActivity.this,MainActivity.class);
											in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
											startActivity(in);
										}else{
											message(p1.getException().getMessage());
										}
										closeLoading();
									}
							});
						}
					}
				}
		});
	}
	private void showLoading()
	{
		AlertDialog.Builder pdg=new AlertDialog.Builder(this);
		View v=LayoutInflater.from(this).inflate(R.layout.activity_load, null);
		ProgressBar pb=v.findViewById(R.id.progressBar);
		pdg.setView(v);
		pdg.setCancelable(false);
		pdialog = pdg.create();
		if (pdialog.getWindow() != null)
		{
			pdialog.getWindow().getAttributes().windowAnimations = R.style.zoomAlert;
			pdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		}
		pb.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate));
		pdialog.show();
	}
	private void closeLoading()
	{
		if (pdialog.isShowing())
		{
			pdialog.dismiss();
		}
	}
	private String s(EditText et){
		return et.getText().toString();
	}
	private void message(String m){
		Toast.makeText(this,m,Toast.LENGTH_SHORT).show();
	}
}
