package com.example.administrator.oujiademo.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.example.administrator.oujiademo.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 批量获取指定的drawable图片文件
 */
public class DrawableUtil {
    private Context context;

    public DrawableUtil(Context context) {
        this.context = context;
    }

    /**
     * 批量获取指定图片 imageResourceId
     *
     * @param imgName 图片名字的部分或全部
     * @return 图片名字中包含有imgName的所有图片 imageResourceId
     * <p>
     * 使用示例： 如有5张图片: image_0 ,image_1 ,image_3 , image_4 ,image_5 ;
     * 调用方法 getImageResourId("image_")  ,则会得到上面的张图片的imageResourceId
     */
    public List<Integer> getImageResourId(String imgName) {
        List<Integer> imgList = new ArrayList<>();
        Resources resources = context.getResources();
        Field[] fields = R.drawable.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            String name = fields[i].getName();
            if (name.contains(imgName)) {
                int resId = resources.getIdentifier(name, "drawable", context.getPackageName());
                imgList.add(resId);
            }
        }
        return imgList;
    }

    /**
     * 批量获取指定图片 drawable对象
     *
     * @param imgName 图片名字的部分或全部
     * @return 图片名字中包含有imgName的所有图片 drawable对象
     */
    public List<Drawable> getDrawable(String imgName) {
        List<Drawable> imgList = new ArrayList<>();
        Resources resources = context.getResources();
        Field[] fields = R.drawable.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            String name = fields[i].getName();
            if (name.contains(imgName)) {
                int resId = resources.getIdentifier(name, "drawable", context.getPackageName());
                Drawable drawable = resources.getDrawable(resId);
                imgList.add(drawable);
            }
        }
        return imgList;
    }
}
