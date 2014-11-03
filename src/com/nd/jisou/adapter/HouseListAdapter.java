package com.nd.jisou.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nd.jisou.R;
import com.nd.jisou.activity.HouseDetailActivity;
import com.nd.jisou.data.json.HouseInfo;
import com.nd.jisou.utils.AsyncImageLoader;
import com.nd.jisou.utils.StringUtils;


public class HouseListAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<HouseInfo> mDataList=new ArrayList<HouseInfo>();
	private Handler mHanlder=new Handler();
	private AsyncImageLoader mImageLoader=null;
	private ListView mListView;

	public HouseListAdapter(ListView listView, Context context){
		mContext = context;
		mImageLoader= new AsyncImageLoader(context);
		mListView = listView;
		mListView.setOnItemClickListener(onItemClickListener);
	}
	

	public List<HouseInfo> getDataList(){
		return mDataList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDataList.size();
	}

	@Override
	public HouseInfo getItem(int position) {
		if (position < mDataList.size()) {
			return mDataList.get(position);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		HouseItemHolder holder=null;
		if(convertView==null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.house_list_item, parent, false);
			holder = new HouseItemHolder();
			holder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
			holder.tv_apartment = (TextView) convertView.findViewById(R.id.tv_apartment);
			holder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
			holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
			convertView.setTag(holder);
		}
		else{
			holder = (HouseItemHolder)convertView.getTag();
		}
		
		HouseInfo houseItem = getItem(position);
		if(houseItem!=null){
			holder.tv_title.setText(houseItem.getTitle());
			holder.tv_address.setText(houseItem.getAddress() +" "+houseItem.getName());
			holder.tv_price.setText(String.valueOf(houseItem.getPrice()+"Íò"));
			holder.tv_size.setText(houseItem.getSize()+"Æ½Ã×");
			holder.tv_apartment.setText(houseItem.getApartment());
			String picUrl = StringUtils.getAssertImageUrl(houseItem.getPicIndex());
			holder.iv_pic.setTag(picUrl);
			holder.iv_pic.setBackgroundResource(R.drawable.post_list_thumb_loading);
			mImageLoader.loadAssestImage(holder.iv_pic, picUrl, 100,100);
		}
		
		return convertView;
	}
	
	private OnItemClickListener onItemClickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			HouseInfo houseItem = getItem(position);
			if(houseItem!=null){
				Intent newItent =new Intent(mContext, HouseDetailActivity.class);
				newItent.putExtra(HouseDetailActivity.EXTRA_HOUSE_NAME, houseItem.getName());
				((Activity)mContext).startActivity(newItent);
			}
		}
	};
	
	
	class HouseItemHolder {
		public ImageView iv_pic;
		public TextView tv_title;
		public TextView tv_address;
		public TextView tv_apartment;
		public TextView tv_size;
		public TextView tv_price;
	}

}
