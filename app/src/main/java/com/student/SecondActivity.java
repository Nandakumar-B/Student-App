package com.student;

import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.student.Adapters.SubjectAdapter;
import com.student.Medium.Subject;
import java.util.*;
import me.anwarshahriar.calligrapher.*;
import android.view.animation.*;
import android.content.*;
import com.google.firebase.database.*;
import android.net.*;

import androidx.annotation.NonNull;

public class SecondActivity extends Activity implements OnClickListener
{
	private Calligrapher call;
	private Typeface tpb,tpl;
	
	private String stSub,stTch=null;
	
	private TextView tvSubName,tvTeachName,
	        btnNote,btnLink,btnHw,btnTime;
	private ImageView imgIcon,imgBtn;
	private ListView lvNote,lvLink,lvHw,lvTime;
	
	private SubjectAdapter noteAd,linkAd,hwAd,timeAd;
	private List<Subject> noteList,linkList,hwList,timeList;

	private FirebaseDatabase db;
	private DatabaseReference ref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		tvSubName=findViewById(R.id.txt_subname_second);
		tvTeachName=findViewById(R.id.txt_teachname_second);
		imgIcon=findViewById(R.id.img_icon_second);
		imgBtn=findViewById(R.id.img_btn_left);
		btnNote=findViewById(R.id.btn_note);
		btnLink=findViewById(R.id.btn_link);
		btnHw=findViewById(R.id.btn_hw);
		btnTime=findViewById(R.id.btn_time);
		lvNote=findViewById(R.id.list_note);
		lvLink=findViewById(R.id.list_link);
		lvHw=findViewById(R.id.list_hw);
		lvTime=findViewById(R.id.list_time);
		
		imgBtn.setOnClickListener(this);
		btnNote.setOnClickListener(this);
		btnLink.setOnClickListener(this);
		btnHw.setOnClickListener(this);
		btnTime.setOnClickListener(this);
		
		db=FirebaseDatabase.getInstance();
		ref=db.getReference("student");
		
		call=new Calligrapher(this);
		call.setFont(this,"Poppins-Regular.ttf",true);
		tpb=Typeface.createFromAsset(getAssets(),"Poppins-Bold.ttf");
		tpl=Typeface.createFromAsset(getAssets(),"Poppins-Light.ttf");
		tvSubName.setTypeface(tpb);
		
		stSub=getIntent().getStringExtra("sub_name");
		stTch=getIntent().getStringExtra("teach_name");
		if(stSub!=null && stTch!=null){
			switch (stSub) {
				case "Mobile Communication":
					imgIcon.setBackgroundResource(R.drawable.ic_mobile);
					readData("mobile/note", "note");
					readData("mobile/link", "link");
					readData("mobile/hw", "hw");
					readData("mobile/time", "time");
					break;
				case "Web Programming":
					imgIcon.setBackgroundResource(R.drawable.ic_web);
					readData("web/note", "note");
					readData("web/link", "link");
					readData("web/hw", "hw");
					readData("web/time", "time");
					break;
				case "Micro Controllers":
					imgIcon.setBackgroundResource(R.drawable.ic_micro);
					readData("micro/note", "note");
					readData("micro/link", "link");
					readData("micro/hw", "hw");
					readData("micro/time", "time");
					break;
				case "NIM":
					imgIcon.setBackgroundResource(R.drawable.ic_net);
					readData("nim/note", "note");
					readData("nim/link", "link");
					readData("nim/hw", "hw");
					readData("nim/time", "time");
					break;
			}
			tvTeachName.setText(stTch);
			tvSubName.setText(stSub);
		}
		noteList=new ArrayList<>();
		linkList=new ArrayList<>();
		hwList=new ArrayList<>();
		timeList=new ArrayList<>();
		//for(int i=0;i<10;i++){
			//noteList.add(new Subject(R.drawable.ic_pdf,"Module-1 (Communications)","posted on : 09-11-2021",""));
			//linkList.add(new Subject(R.drawable.ic_link,"Morning Class (10:00 AM)","posted on : 10-11-2021",""));
			//hwList.add(new Subject(R.drawable.ic_pdf,"Home Work 1.1","posed on : 10-11-2021",""));
			//timeList.add(new Subject(R.drawable.ic_time,"Class on 11-10-21 :: 10 AM to 11 AM","posted on : 10-11-2021",""));
		//}
		//noteAd=new SubjectAdapter(this,R.layout.list_subject,noteList);
		//linkAd=new SubjectAdapter(this,R.layout.list_subject,linkList);
		//hwAd=new SubjectAdapter(this,R.layout.list_subject,hwList);
		//timeAd=new SubjectAdapter(this,R.layout.list_subject,timeList);
		//lvNote.setAdapter(noteAd);
		//lvLink.setAdapter(linkAd);
	//	lvHw.setAdapter(hwAd);
		//lvTime.setAdapter(timeAd);
		
		//ref.child("mobile/note").push().setValue(new Subject(1,"Module","09",""));
		
		linkClick(lvNote);
		linkClick(lvLink);
		linkClick(lvHw);
	}
	private void readData(String path,final String type){
		ref.child(path).addValueEventListener(new ValueEventListener(){

				@Override
				public void onDataChange(@NonNull DataSnapshot p1)
				{
					switch (type) {
						case "note":
							noteList.clear();
							for (DataSnapshot shot : p1.getChildren()) {
								Subject sub = shot.getValue(Subject.class);
								noteList.add(new Subject(R.drawable.ic_pdf, sub.getSubName(), "Posted on : " + sub.getSubTeacher(), sub.getSubUrl()));
							}
							noteAd = new SubjectAdapter(SecondActivity.this, R.layout.list_subject, noteList);
							lvNote.setAdapter(noteAd);
							break;
						case "link":
							linkList.clear();
							for (DataSnapshot shot : p1.getChildren()) {
								Subject sub = shot.getValue(Subject.class);
								linkList.add(new Subject(R.drawable.ic_link, sub.getSubName(), "Posted on : " + sub.getSubTeacher(), sub.getSubUrl()));
							}
							linkAd = new SubjectAdapter(SecondActivity.this, R.layout.list_subject, linkList);
							lvLink.setAdapter(linkAd);
							break;
						case "hw":
							hwList.clear();
							for (DataSnapshot shot : p1.getChildren()) {
								Subject sub = shot.getValue(Subject.class);
								hwList.add(new Subject(R.drawable.ic_pdf, sub.getSubName(), "Posted on : " + sub.getSubTeacher(), sub.getSubUrl()));
							}
							hwAd = new SubjectAdapter(SecondActivity.this, R.layout.list_subject, hwList);
							lvHw.setAdapter(hwAd);
							break;
						case "time":
							timeList.clear();
							for (DataSnapshot shot : p1.getChildren()) {
								Subject sub = shot.getValue(Subject.class);
								timeList.add(new Subject(R.drawable.ic_time, sub.getSubName(), "Posted on : " + sub.getSubTeacher(), ""));
							}
							timeAd = new SubjectAdapter(SecondActivity.this, R.layout.list_subject, timeList);
							lvTime.setAdapter(timeAd);
							break;
					}
				}

				@Override
				public void onCancelled(@NonNull DatabaseError p1)
				{
					message(p1.getMessage());
				}
		});
	}
	private void linkClick(ListView lv){
		lv.setOnItemClickListener(new ListView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					TextView tv=p2.findViewById(R.id.txt_hide);
					Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse(tv.getText().toString()));
					startActivity(in);
				}
			});
	}
	@Override
	public void onClick(View p1)
	{
		switch(p1.getId()){
			case R.id.img_btn_left:
				Intent in=new Intent(this,MainActivity.class);
				in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(in);
				break;
			case R.id.btn_note:
			    showBtn(btnNote,lvNote);
				hideBtn(btnLink,lvLink);
				hideBtn(btnHw,lvHw);
				hideBtn(btnTime,lvTime);
				break;
			case R.id.btn_link:
				showBtn(btnLink,lvLink);
				hideBtn(btnNote,lvNote);
				hideBtn(btnHw,lvHw);
				hideBtn(btnTime,lvTime);
				break;
			case R.id.btn_hw:
				showBtn(btnHw,lvHw);
				hideBtn(btnLink,lvLink);
				hideBtn(btnNote,lvNote);
				hideBtn(btnTime,lvTime);
				break;
			case R.id.btn_time:
				showBtn(btnTime,lvTime);
				hideBtn(btnLink,lvLink);
				hideBtn(btnHw,lvHw);
				hideBtn(btnNote,lvNote);
				break;
		}
	}
	private void showBtn(TextView tv,ListView lv){
		tv.setBackgroundResource(R.drawable.solid_background);
		tv.setTextColor(Color.WHITE);
		lv.setVisibility(View.VISIBLE);
		lv.startAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_up));
	}
	private void hideBtn(TextView tv,ListView lv){
		tv.setBackgroundResource(android.R.color.transparent);
		tv.setTextColor(Color.parseColor("#666666"));
		lv.setVisibility(View.GONE);
	}
	private void message(String m){
		Toast.makeText(this,m,Toast.LENGTH_SHORT).show();
	}
}
