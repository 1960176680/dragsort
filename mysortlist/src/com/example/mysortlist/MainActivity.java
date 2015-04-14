package com.example.mysortlist;

import java.util.ArrayList;
import java.util.List;

import com.mobeta.android.dslv.DragSortListView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	//��ע�б����
	private ArrayList<Column> followList = new ArrayList<Column>();
	private DragSortListView followListView;
	private FollowAdapter mFollowAdapter;
	//δ��ע�б����
	private ArrayList<Column> notFollowList = new ArrayList<Column>();
	private ListView notFollowListView;
	private NotFollowAdapter mNotFollowAdapter;
	
	//����һ����ʱ���� �������е�ֵ ��Ϊ�ں����б�Ϊ��ʱ��������
	private ArrayList<Column> allList = new ArrayList<Column>();;

	// ���������ֻ��϶�ͣ�µ�ʱ�򴥷�
	private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
		@Override
		public void drop(int from, int to) {// from to �ֱ��ʾ ���϶��ؼ�ԭλ�� ��Ŀ��λ��
			if (from != to) {
				Column column = (Column) mFollowAdapter.getItem(from);// �õ�listview��������
				mFollowAdapter.remove(from);// ���������С�ԭλ�á������ݡ�
				mFollowAdapter.insert(column, to);// ��Ŀ��λ���в��뱻�϶��Ŀؼ���
			}
		}
	};
	
	// ɾ���������������߲�žʹ�����ɾ��item������
	private DragSortListView.RemoveListener onRemove = new DragSortListView.RemoveListener() {
		@Override
		public void remove(int which) {
			
			//δ��ע�б�����һ��
			if (followList.size() != 0) {
				Column column = (Column) mFollowAdapter.getItem(which);
				editor.putBoolean(column.name, false);
				notFollowList.add(column);
				mNotFollowAdapter.notifyDataSetChanged();
			}else {
				notFollowList.clear();
				notFollowList.addAll(allList);
				mNotFollowAdapter.notifyDataSetChanged();
			}
			//��ɾ��
			mFollowAdapter.remove(which);
			
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // �����ޱ���
		setContentView(R.layout.activity_main);
		initView();
		initData();
		initSavaData();
		
		followListView.setDropListener(onDrop);
		followListView.setRemoveListener(onRemove);

		mFollowAdapter = new FollowAdapter(MainActivity.this, followList);
		followListView.setAdapter(mFollowAdapter);
		followListView.setDragEnabled(true); // �����Ƿ���϶���

		mNotFollowAdapter = new NotFollowAdapter(MainActivity.this, notFollowList);
		notFollowListView.setAdapter(mNotFollowAdapter);
	}

	private void initView() {
		followListView = (DragSortListView) findViewById(R.id.follow_list);
		notFollowListView = (ListView) findViewById(R.id.not_follow_list);
	}

	private void initData() {
		// followList = NewsCategoryDao.getInstance(context).queryAll();
		Column column1 = new Column();
		column1.id = 1;
		column1.name = "��������";
		followList.add(column1);
		allList.add(column1);

		Column column2 = new Column();
		column2.id = 2;
		column2.name = "NBA";
		followList.add(column2);
		allList.add(column2);

		Column column3 = new Column();
		column3.id = 3;
		column3.name = "�й�����";
		followList.add(column3);
		allList.add(column3);

		Column column4 = new Column();
		column4.id = 4;
		column4.name = "�й�����";
		followList.add(column4);
		allList.add(column4);

		Column column5 = new Column();
		column5.id = 5;
		column5.name = "��Ƶ";
		followList.add(column5);
		allList.add(column5);
	}

	private void initSavaData() {
		preferences = getSharedPreferences("column",
				MODE_WORLD_READABLE);
		editor = preferences.edit();
		for (int i = 0; i < followList.size(); i++) {
			editor.putBoolean(followList.get(i).name, true);
		}
		editor.commit();
	}

	public FollowAdapter getFollowAdapter() {
		return mFollowAdapter;
	}

	public ArrayList<Column> getFollowList() {
		return followList;
	}

	public NotFollowAdapter getNotFollowAdapter() {
		return mNotFollowAdapter;
	}

	public ArrayList<Column> getNotFollowList() {
		return notFollowList;
	}

}
