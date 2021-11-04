package com.student.Adapters;

import android.widget.*;
import com.student.Medium.Subject;
import android.view.*;
import android.content.*;
import java.util.*;
import com.student.R;
import android.graphics.*;
import android.view.animation.*;

public class SubjectAdapter extends ArrayAdapter<Subject>
{
	private Context mCtx;
	private int mRes;
	private List<Subject> mList;
	
	public SubjectAdapter(Context ctx,int res,List<Subject> list){
		super(ctx,res,list);
		this.mCtx=ctx;
		this.mRes=res;
		this.mList=list;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent)
	{
		Typeface tpb,tpr;
		ViewHolder vh;
		if(view==null){
			view=LayoutInflater.from(mCtx).inflate(mRes,null);
			vh=new ViewHolder(view);
			view.setTag(vh);
		}
		else{
			vh=(ViewHolder)view.getTag();
		}
		tpb=Typeface.createFromAsset(mCtx.getAssets(),"Poppins-Bold.ttf");
		tpr=Typeface.createFromAsset(mCtx.getAssets(),"Poppins-Regular.ttf");
		vh.imgIcon.setBackgroundResource(getItem(position).getImg());
		vh.tvSubName.setText(getItem(position).getSubName());
		vh.tvSubTeacher.setText(getItem(position).getSubTeacher());
		vh.tvHide.setText(getItem(position).getSubUrl());
		vh.tvSubName.setTypeface(tpb);
		vh.tvSubTeacher.setTypeface(tpr);
		vh.pan.startAnimation(AnimationUtils.loadAnimation(mCtx,R.anim.zoom_in));
		return view;
	}
	class ViewHolder{
		ImageView imgIcon;
		TextView tvSubName,tvSubTeacher,tvHide;
		LinearLayout pan;
		public ViewHolder(View v){
			pan=v.findViewById(R.id.pan_list);
			imgIcon=v.findViewById(R.id.img_icon_main);
			tvSubName=v.findViewById(R.id.txt_subname_main);
			tvSubTeacher=v.findViewById(R.id.txt_teachname_main);
			tvHide=v.findViewById(R.id.txt_hide);
		}
	}
}
