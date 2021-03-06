package com.utsavbucky.polygonview;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import com.utsavbucky.polygonview.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class PolygonView extends View {

    private int numberOfSides = 6;
    private int orientation = 1;
    private float borderRadius ;
    private float borderWidth ;
    private Drawable backgroundBitmap ;
    private float shadow ;
    private float padding;
    private float dashWidth;
    private float dashGap;
    private int bitmapRotation;
    private int borderColour = getResources().getColor(R.color.black);
    private int backgroundColour = getResources().getColor(R.color.grey) ;

    public PolygonView(Context context) {
        super(context);
        init(null);
    }

    public PolygonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PolygonView(Context context, @Nullable  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }



    public int getBitmapRotation() {
        return bitmapRotation;
    }

    public void setBitmapRotation(int bitmapRotation) {
        this.bitmapRotation = bitmapRotation;
    }

    public float getPadding() {
        return padding;
    }

    public void setPadding(float padding) {
        this.padding = padding;
    }



    public int getNumberOfSides() {
        return numberOfSides;
    }

    public void setNumberOfSides(int numberOfSides) {
        this.numberOfSides = numberOfSides;
        postInvalidate();
        
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
        postInvalidate();
        
    }

    public float getBorderRadius() {
        return borderRadius;
    }

    public float getDashGap() {
        return dashGap;
    }

    public void setDashGap(float dashGap) {
        this.dashGap = dashGap;
    }

    public void setBorderRadius(float borderRadius) {
        this.borderRadius = borderRadius;
        postInvalidate();
        
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        postInvalidate();
        
    }

    public Drawable getBackgroundBitmap() {
        return backgroundBitmap;
    }

    public void setBackgroundBitmap(Drawable backgroundBitmap) {
        this.backgroundBitmap = backgroundBitmap;
        postInvalidate();
        
    }


    public float getShadow() {
        return shadow;
    }

    public void setShadow(float shadow) {
        this.shadow = shadow;
        postInvalidate();
        
    }

    public int getBorderColour() {
        return borderColour;
    }

    public void setBorderColour(int borderColour) {
        this.borderColour = borderColour;
        postInvalidate();
        
    }

    public int getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(int backgroundColour) {
        this.backgroundColour = backgroundColour;
        postInvalidate();
        
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Orientation.HORIZONTAL, Orientation.VERTICAL})
    public @interface Orientation {
        int HORIZONTAL = 0;
        int VERTICAL = 1;
    }

    private static Paint dashPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    public float getDashWidth() {
        return dashWidth;
    }

    public void setDashWidth(float dashWidth) {
        this.dashWidth = dashWidth;
    }


    private void init(AttributeSet attrs) {

       // setPaints();

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PolygonView);
        try {
            orientation = a.getInt(R.styleable.PolygonView_orientation, Orientation.VERTICAL);
            borderRadius = a.getDimensionPixelSize(R.styleable.PolygonView_border_radius, 0);
            borderColour = a.getColor(R.styleable.PolygonView_border_colour, getContext().getResources().getColor(R.color.grey));
            borderWidth = a.getDimensionPixelSize(R.styleable.PolygonView_border_width, 0);
            shadow = a.getDimension(R.styleable.PolygonView_shadow, 0);
            backgroundBitmap = a.getDrawable(R.styleable.PolygonView_background_bitmap);
            backgroundColour = a.getColor(R.styleable.PolygonView_background_colour, 0);
            numberOfSides = a.getInt(R.styleable.PolygonView_num_of_sides, 0);
            padding =  a.getDimension(R.styleable.PolygonView_padding, 0);
            dashWidth =  a.getDimension(R.styleable.PolygonView_dash_width, 0);
            dashGap =  a.getDimension(R.styleable.PolygonView_dash_gap, 0);
            bitmapRotation =  a.getInt(R.styleable.PolygonView_bitmap_rotation, 0);

        } finally {
            a.recycle();
        }

    }
    private void setPaints()
    {
        CornerPathEffect cornerPathEffect =  new CornerPathEffect(borderRadius);
        dashPaint.setPathEffect(cornerPathEffect);
        dashPaint.setColor(backgroundColour);
        dashPaint.setStyle(Style.FILL);

        borderPaint.setColor(borderColour);
        borderPaint.setStyle(Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setShadowLayer(shadow, 0, 0, getContext().getResources().getColor(R.color.grey));

        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    private Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds(0, 0, widthPixels, heightPixels);
        drawable.draw(canvas);

        return mutableBitmap;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onDraw(Canvas canvas) {

        int w = getWidth();
        int h = getHeight();
        setPaints();
        CornerPathEffect cornerPathEffect =  new CornerPathEffect(borderRadius);
        PathEffect[] pathEffects = new PathEffect[3];
        pathEffects[0] = new DashPathEffect(new float[] { dashWidth, dashGap}, 0);
        pathEffects[1] = cornerPathEffect;
        pathEffects[2] = new ComposePathEffect(pathEffects[0],pathEffects[1]);
        borderPaint.setPathEffect(pathEffects[2]);

        if(numberOfSides>3) {

            Paint bpaint = new Paint();
            bpaint.setAntiAlias(true);
            bpaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            canvas.drawPath(createClipPath(w, h, canvas), dashPaint);
            canvas.restore();
            if (backgroundBitmap != null) {
                Bitmap mBitmap = convertToBitmap(backgroundBitmap, w, h);
                Matrix matrix = new Matrix();
                matrix.postRotate(bitmapRotation);
                Bitmap rotatedBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
                canvas.drawBitmap(rotatedBitmap, 0, 0, bpaint);
            }
            canvas.drawPath(createClipPath(w, h, canvas), borderPaint);
            canvas.restore();
        }else {
            canvas.drawLine(0,h/2,w,h/2,borderPaint);
            canvas.restore();
        }



    }

    private Path createClipPath(int w, int h, Canvas canvas) {

        /*final float section = (float) (2.0 * Math.PI / numberOfSides);
        int radius = width / 2;
        radius = (int) (radius - padding-borderRadius-10*width/200);
        final int centerX = width / 2;
        final int centerY = height / 2;

        final Path polygonPath = new Path();
        polygonPath.moveTo((centerX + radius * (float) Math.cos(0)), (centerY + radius * (float) Math.sin(0)));

        for (int i = 1; i < numberOfSides; i++) {
            polygonPath.lineTo((centerX + radius * (float) Math.cos(section * i)),
                    (centerY + radius * (float) Math.sin(section * i)));
        }

        Matrix mMatrix = new Matrix();
        RectF bounds = new RectF();
        polygonPath.computeBounds(bounds, true);
        mMatrix.postRotate(270, bounds.centerX(), bounds.centerY());
        polygonPath.transform(mMatrix);

        polygonPath.close();*/

        final int x = w / 2;
        final int y = h / 2;
        float a = ((float) Math.PI *2) / numberOfSides * (true ? -1 : 1);
        canvas.save();
        canvas.translate(x, y);
        canvas.rotate(270);
        int radius = w / 2;
        radius = (int) (radius - padding - 10*w/200);
        Path path = new Path();
        path.moveTo(radius, 0);
        for(int i = 1; i < numberOfSides; i++) {
            path.lineTo(radius * (float) Math.cos(a * i), radius * (float) Math.sin(a * i));
        }
        path.close();
        return path;
    }

}


