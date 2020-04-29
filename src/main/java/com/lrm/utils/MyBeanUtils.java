package com.lrm.utils;

import com.lrm.po.Blog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MyBeanUtils {
    public static String[] getNullProperties(Object object)
    {
      Field fields[]= object.getClass().getDeclaredFields();
        List<String> nulls=new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if(field.get(object)==null)
                {
                    nulls.add(field.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
      return nulls.toArray(new String[nulls.size()]);
    }

}
