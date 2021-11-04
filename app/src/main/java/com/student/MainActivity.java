package com.student;

import android.app.*;
import android.os.*;
import me.anwarshahriar.calligrapher.*;
import android.graphics.*;
import android.widget.*;
import com.student.Adapters.*;
import com.student.Medium.Subject;
import java.util.*;
import android.view.*;
import android.content.*;
import com.google.firebase.database.*;
import com.google.firebase.auth.*;

public class MainActivity extends Activity 
{
	private TextView tvUName,tvHSub;
	private ListView lv;
	private ImageView imgOut;
	
	private Calligrapher call;
	private Typeface tpb,tpl;
	private SubjectAdapter sad;
	private List<Subject> subList;
	
	private boolean isPress=false;
	
	private FirebaseAuth auth;
	private FirebaseDatabase db;
	private DatabaseReference ref;
	
	@Override
	public void onBackPressed()
	{
		if(isPress){
		 super.onBackPressed();
		 return;
		}
		isPress=true;
		message("Press once more");
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run(){
				isPress=false;
			}
		},2000);
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		tvUName=findViewById(R.id.txt_name_pro);
		tvHSub=findViewById(R.id.txt_head_subject);
		lv=findViewById(R.id.list_subject);
		imgOut=findViewById(R.id.img_out);
		
		auth=FirebaseAuth.getInstance();
		db=FirebaseDatabase.getInstance();
		ref=db.getReference("student");
		//ref.child("users/"+auth.getCurrentUser().getUid()+"/name").setValue("Rakesh M");
		//ref.child("web").setValue(new Subject(R.drawable.ic_hat,"Mobile","09/06/2021"));
		
		call=new Calligrapher(this);
		call.setFont(this,"Poppins-Regular.ttf",true);
		tpl=Typeface.createFromAsset(getAssets(),"Poppins-Light.ttf");
		tpb=Typeface.createFromAsset(getAssets(),"Poppins-Bold.ttf");
	
		tvUName.setTypeface(tpb);
		tvHSub.setTypeface(tpb);
		int[] img={R.drawable.ic_mobile,R.drawable.ic_web,R.drawable.ic_micro,R.drawable.ic_net};
		//String[] teacher={"Seena","Manila","Sreenivasan","Thahira"};
		String[] teacher={"Malar S","Mohan C","Vivek S","Savitha K"};
		String[] subject={"Mobile Communication","Web Programming","Micro Controllers","NIM"};
		subList=new ArrayList<>();
		for(int i=0;i<4;i++){
			subList.add(new Subject(img[i],subject[i],teacher[i],""));
		}
		sad=new SubjectAdapter(this,R.layout.list_subject,subList);
		lv.setAdapter(sad);
		
		lv.setOnItemClickListener(new ListView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					TextView tvn=p2.findViewById(R.id.txt_subname_main);
					TextView tvt=p2.findViewById(R.id.txt_teachname_main);
					Intent in=new Intent(MainActivity.this,SecondActivity.class);
					in.putExtra("sub_name",tvn.getText().toString());
					in.putExtra("teach_name",tvt.getText().toString());
					startActivity(in);
				}
		});
		imgOut.setOnClickListener(new ImageView.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					auth.signOut();
					Intent in=new Intent(MainActivity.this,SplashActivity.class);
					in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(in);
				}
		});
		ref.child("users/" + auth.getCurrentUser().getUid()+"/name").addValueEventListener(new ValueEventListener(){

				@Override
				public void onDataChange(DataSnapshot p1)
				{
					tvUName.setText(p1.getValue(String.class));
				}
				@Override
				public void onCancelled(DatabaseError p1)
				{
					message(p1.getMessage());
				}
		});
    }
	private void message(String m){
		Toast.makeText(this,m,Toast.LENGTH_SHORT).show();
	}
}
