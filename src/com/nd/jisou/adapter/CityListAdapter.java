package com.nd.jisou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.nd.jisou.R;
import com.nd.jisou.data.CityData;

public class CityListAdapter extends BaseAdapter {
	
	private Context mContext;

	public CityListAdapter(Context context){
		mContext = context;
	}
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return CityData.getCityList().size();
	}

	@Override
	public String getItem(int position) {
		if (position < CityData.getCityList().size()) {
			return CityData.getCityList().get(position);
		} else {
			return "";
		}
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		CityItemHolder holder=null;
		if(convertView==null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.city_list_item, parent, false);
			holder = new CityItemHolder();
			holder.tv_city = (TextView) convertView.findViewById(R.id.tv_city);
			convertView.setTag(holder);
		}
		else{
			holder = (CityItemHolder)convertView.getTag();
		}
		
		holder.tv_city.setText(getItem(position));
		
		return convertView;
	}
	
	class CityItemHolder {
		
		public TextView tv_city;
	}

}
