package com.xbting.lp.widget;


import java.util.ArrayList;
import java.util.List;

import com.xbting.lp.BuildConfig;
import com.xbting.lp.R;
import com.xbting.lp.widget.LockPatternView.Cell;



import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
/**
 * 
 * @author xbting
 * @version：
 */
public class LockPatternPreView extends View {
	private static final String TAG ="LockPatternPreView";
	/**手势密码图案*/
	private List<Cell> pattern;
	/**圆点半径*/
	private float radius ;
	private float cellPadding;
	/**圆点的填充颜色*/
	private int fillColor;
	/**空心圆点的环颜色*/
	private int strokeColor;
	/**空心圆点的环宽度*/
	private float strokeWidth;

	private float mSquareHeight;// 每个cell 的高
	private float mSquareWidth;// 每个cell 的宽
	private final Paint mPaintStroke = new Paint();
	private final Paint mPaintFill = new Paint();
	private List<Integer> selectList;

	/**
	 * @param context
	 */
	public LockPatternPreView(Context context) {
		super(context);
		Resources res = getResources();
		radius  = res.getDimension(R.dimen.default_lock_prvview_radius);
		cellPadding = res.getDimension(R.dimen.default_lock_preview_cell_padding);
		strokeWidth= res.getDimension(R.dimen.default_lock_preview_stroke_width);
		fillColor = res.getColor(R.color.default_lock_preview_view_fill_color);
		strokeWidth = res.getColor(R.color.default_lock_preview_view_stroke_color);
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public LockPatternPreView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		getAttrs(context, attrs);
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public LockPatternPreView(Context context, AttributeSet attrs) {
		super(context, attrs);
		getAttrs(context, attrs);
		init();
	}
	
	/**
	 * 
	 * 获取xml属性
	 * 
	 * @param context
	 * @param attrs
	 * void
	 */
	private void getAttrs(Context context, AttributeSet attrs){
		Resources res = getResources();
		float defaultRadius  = res.getDimension(R.dimen.default_lock_prvview_radius);
		float defaultCellPadding = res.getDimension(R.dimen.default_lock_preview_cell_padding);
		float defaultStrokeWidth= res.getDimension(R.dimen.default_lock_preview_stroke_width);
		int defaultFillColor = res.getColor(R.color.default_lock_preview_view_fill_color);
		int defaultStrokeColor = res.getColor(R.color.default_lock_preview_view_stroke_color);
		
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LockPatternPreView);
		radius = ta.getDimension(R.styleable.LockPatternPreView_radius, defaultRadius);
		cellPadding = ta.getDimension(R.styleable.LockPatternPreView_cellPadding, defaultCellPadding);
		strokeWidth = ta.getDimension(R.styleable.LockPatternPreView_strokeWidth, defaultStrokeWidth);
		fillColor = ta.getColor(R.styleable.LockPatternPreView_fillColor, defaultFillColor);
		strokeColor = ta.getColor(R.styleable.LockPatternPreView_strokeColor, defaultStrokeColor);
		ta.recycle();
	}

	/**
	 * 初始化
	 * 
	 * void
	 */
	private void init() {
		selectList = new ArrayList<Integer>();
		mPaintStroke.setStyle(Style.STROKE);
		mPaintStroke.setColor(strokeColor);
		mPaintStroke.setStrokeWidth(strokeWidth);
		
		mPaintFill.setStyle(Style.FILL);
		mPaintFill.setColor(fillColor);
	}
	
	/**
	 * @return the pattern
	 */
	public List<Cell> getPattern() {
		return pattern;
	}

	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern(List<Cell> pattern) {
		this.pattern = pattern;
	}

	/**
	 * @return the radius
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(float radius) {
		this.radius = radius;
	}

	/**
	 * @return the cellPadding
	 */
	public float getCellPadding() {
		return cellPadding;
	}

	/**
	 * @param cellPadding the cellPadding to set
	 */
	public void setCellPadding(float cellPadding) {
		this.cellPadding = cellPadding;
	}

	/**
	 * @return the fillColor
	 */
	public int getFillColor() {
		return fillColor;
	}

	/**
	 * @param fillColor the fillColor to set
	 */
	public void setFillColor(int fillColor) {
		this.fillColor = fillColor;
	}

	/**
	 * @return the strokeColor
	 */
	public int getStrokeColor() {
		return strokeColor;
	}

	/**
	 * @param strokeColor the strokeColor to set
	 */
	public void setStrokeColor(int strokeColor) {
		this.strokeColor = strokeColor;
	}

	/**
	 * @return the strokeWidth
	 */
	public float getStrokeWidth() {
		return strokeWidth;
	}

	/**
	 * @param strokeWidth the strokeWidth to set
	 */
	public void setStrokeWidth(float strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	/**
	 * 根据手势图案更新新view
	 * 
	 * @param mPattern
	 * void
	 */
	public void updateView(List<Cell> mPattern) {
		setPattern(mPattern);
		if(mPattern!=null){
			if(BuildConfig.DEBUG){
				Log.i(TAG, "update----"+mPattern.toString());
			}
		}
		invalidate();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
//		super.onSizeChanged(w, h, oldw, oldh);
		Log.d(TAG, "onSizeChanged---w:"+w+"   h:"+h+"  oldw:"+oldw+"   oldh:"+oldh);
		final int width = w - getPaddingLeft() - getPaddingRight();
		mSquareWidth = width / 3.0f;
		Log.i(TAG, "onSizeChanged---mSquareWidth:"+mSquareWidth);
		final int height = h - getPaddingTop() - getPaddingBottom();
		mSquareHeight = height / 3.0f;
		Log.i(TAG, "onSizeChanged---mSquareHeight:"+mSquareHeight);

	}

	@Override
	protected int getSuggestedMinimumWidth() {
		// TODO Auto-generated method stub
		// return super.getSuggestedMinimumWidth();
		Log.d(TAG, "getSuggestedMinimumWidth---------");
		return (int) (3 * 2 * radius + cellPadding * 6);
	}

	@Override
	protected int getSuggestedMinimumHeight() {
		Log.d(TAG, "getSuggestedMinimumHeight---------");
		// TODO Auto-generated method stub
		// return super.getSuggestedMinimumHeight();
		return (int) (3 * 2 * radius + cellPadding * 6);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.d(TAG, "onMeasure---------");
		final int minimumWidth = getSuggestedMinimumWidth();
		final int minimumHeight = getSuggestedMinimumHeight();
		int viewWidth = resolveMeasured(widthMeasureSpec, minimumWidth);
		int viewHeight = resolveMeasured(heightMeasureSpec, minimumHeight);
		
		viewWidth = viewHeight = Math.min(viewWidth, viewHeight);
		Log.i(TAG, "viewWidth = viewHeight:"+viewHeight);
		setMeasuredDimension(viewWidth, viewHeight);
	}
	
	private int resolveMeasured(int measureSpec, int desired) {
		int result = 0;
		int specSize = MeasureSpec.getSize(measureSpec);
		switch (MeasureSpec.getMode(measureSpec)) {
		case MeasureSpec.UNSPECIFIED:
			result = desired;
			break;
		case MeasureSpec.AT_MOST:
			result = Math.max(specSize, desired);
			break;
		case MeasureSpec.EXACTLY:
		default:
			result = specSize;
		}
		return result;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		float firstX = getPaddingLeft()+mSquareWidth/2.0f;
		float firstY = getPaddingTop() +mSquareHeight/2.0f;
//		Log.i(TAG, "onDraw-----firstX"+firstX+"      firstY:"+firstY);
		if(pattern==null){
			for (int i = 0; i < 9; i++) {
				int row = i/3;
				int col = i%3;
				float dx  = firstX+mSquareWidth*col;
				float dy = firstY+mSquareHeight*row;
//				Log.d("TAG", i+"空心--radius:"+radius+"     dx:"+dx+"  dy:"+dy);
				canvas.drawCircle(dx, dy, radius, mPaintStroke);
			}
		}else{
			selectList.clear();
			for (int i = 0; i < pattern.size(); i++) {
				selectList.add(pattern.get(i).getRow()*3+pattern.get(i).getColumn());
				float dx = firstX+pattern.get(i).getColumn()*mSquareWidth;
				float dy = firstY+pattern.get(i).getRow()*mSquareHeight;
//				Log.d(TAG, i+"实心--radius:"+radius+"     dx:"+dx+"  dy:"+dy);
				canvas.drawCircle(dx, dy, radius, mPaintFill);
			}
			for (int i = 0; i < 9; i++) {
				if(selectList.indexOf(i)==-1){
					int row = i/3;
					int col = i%3;
					float dx  = firstX+mSquareWidth*col;
					float dy = firstY+mSquareHeight*row;
//					Log.d(TAG, i+"空=心--radius:"+radius+"     dx:"+dx+"  dy:"+dy);
					canvas.drawCircle(dx, dy, radius, mPaintStroke);
				}
			}
			
		}
		
	}

}
