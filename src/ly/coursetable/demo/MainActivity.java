package ly.coursetable.demo;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	/** 第一个无内容的格子 */
	protected TextView empty;
	/** 星期一的格子 */
	protected TextView monColum;
	/** 星期二的格子 */
	protected TextView tueColum;
	/** 星期三的格子 */
	protected TextView wedColum;
	/** 星期四的格子 */
	protected TextView thrusColum;
	/** 星期五的格子 */
	protected TextView friColum;
	/** 星期六的格子 */
	protected TextView satColum;
	/** 星期日的格子 */
	protected TextView sunColum;
	/** 课程表body部分布局 */
	protected RelativeLayout course_table_layout;
	/** 屏幕宽度 **/
	protected int screenWidth;
	/** 课程格子平均宽度 **/
	protected int aveWidth;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//获得列头的控件
		empty = (TextView) this.findViewById(R.id.test_empty);
		monColum = (TextView) this.findViewById(R.id.test_monday_course);
		tueColum = (TextView) this.findViewById(R.id.test_tuesday_course);
		wedColum = (TextView) this.findViewById(R.id.test_wednesday_course);
		thrusColum = (TextView) this.findViewById(R.id.test_thursday_course);
		friColum = (TextView) this.findViewById(R.id.test_friday_course);
		satColum  = (TextView) this.findViewById(R.id.test_saturday_course);
		sunColum = (TextView) this.findViewById(R.id.test_sunday_course);
		course_table_layout = (RelativeLayout) this.findViewById(R.id.test_course_rl);
		DisplayMetrics dm = new DisplayMetrics();  
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		//屏幕宽度
		int width = dm.widthPixels;
		//平均宽度
		int aveWidth = width / 8;
		//第一个空白格子设置为25宽
		empty.setWidth(aveWidth * 3/4);
		monColum.setWidth(aveWidth * 33/32 + 1);
		tueColum.setWidth(aveWidth * 33/32 + 1);
		wedColum.setWidth(aveWidth * 33/32 + 1);
		thrusColum.setWidth(aveWidth * 33/32 + 1);
		friColum.setWidth(aveWidth * 33/32 + 1);
		satColum.setWidth(aveWidth * 33/32 + 1);
		sunColum.setWidth(aveWidth * 33/32 + 1);
		this.screenWidth = width;
		this.aveWidth = aveWidth;
		int height = dm.heightPixels;
		int gridHeight = height / 12;
		//设置课表界面
		//动态生成12 * maxCourseNum个textview
		for(int i = 1; i <= 12; i ++){
			
			for(int j = 1; j <= 8; j ++){
				
				TextView tx = new TextView(MainActivity.this);
				tx.setId((i - 1) * 8  + j);
				//除了最后一列，都使用course_text_view_bg背景（最后一列没有右边框）
				if(j < 8)
					tx.setBackgroundDrawable(MainActivity.this.
							getResources().getDrawable(R.drawable.course_text_view_bg));
				else
					tx.setBackgroundDrawable(MainActivity.this.
						getResources().getDrawable(R.drawable.course_table_last_colum));
				//相对布局参数
				RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
						aveWidth * 33 / 32 + 1,
						gridHeight);
				//文字对齐方式
				tx.setGravity(Gravity.CENTER);
				//字体样式
				tx.setTextAppearance(this, R.style.courseTableText);
				//如果是第一列，需要设置课的序号（1 到 12）
				if(j == 1)
				{
					tx.setText(String.valueOf(i));
					rp.width = aveWidth * 3/4;	
					//设置他们的相对位置
					if(i == 1)
						rp.addRule(RelativeLayout.BELOW, empty.getId());
					else
						rp.addRule(RelativeLayout.BELOW, (i - 1) * 8);
				}
				else
				{
					rp.addRule(RelativeLayout.RIGHT_OF, (i - 1) * 8  + j - 1);
					rp.addRule(RelativeLayout.ALIGN_TOP, (i - 1) * 8  + j - 1);
					tx.setText("");
				}
					
				tx.setLayoutParams(rp);
				course_table_layout.addView(tx);
			}
		}
		//五种颜色的背景
		int[] background = {R.drawable.course_info_blue, R.drawable.course_info_green, 
							R.drawable.course_info_red, R.drawable.course_info_red,
							R.drawable.course_info_yellow};
		// 添加课程信息
		TextView courseInfo = new TextView(this);
		courseInfo.setText("软件工程\n@302");
		//该textview的高度根据其节数的跨度来设置
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
				aveWidth * 31 / 32,
				(gridHeight - 5) * 2 );
		//textview的位置由课程开始节数和上课的时间（day of week）确定
		rlp.topMargin = 5 + (2 - 1) * gridHeight;
		rlp.leftMargin = 1;
		// 偏移由这节课是星期几决定
		rlp.addRule(RelativeLayout.RIGHT_OF, 1);
		//字体剧中
		courseInfo.setGravity(Gravity.CENTER);
		// 设置一种背景
		courseInfo.setBackgroundResource(background[1]);
		courseInfo.setTextSize(12);
		courseInfo.setLayoutParams(rlp);
		courseInfo.setTextColor(Color.WHITE);
		//设置不透明度
		courseInfo.getBackground().setAlpha(222);
		course_table_layout.addView(courseInfo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
