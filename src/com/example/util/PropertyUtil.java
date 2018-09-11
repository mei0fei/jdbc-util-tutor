package com.example.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by zhuqiuhui on 2017/11/15.
 */
public class PropertyUtil {

    public static PropertyDescriptor getPropertyDescriptor(Class clazz, String propertyName) {
        StringBuffer sb = new StringBuffer();//����һ���ɱ��ַ�������������������
        Method setMethod = null;
        Method getMethod = null;
        PropertyDescriptor pd = null;
        try {
            Field f = clazz.getDeclaredField(propertyName);//�����ֶ�������ȡ�ֶ�
            if (f != null) {
                //���������ĺ�׺
                String methodEnd = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
                sb.append("set" + methodEnd);
                //����set����
                setMethod = clazz.getDeclaredMethod(sb.toString(), new Class[]{f.getType()});
                sb.delete(0, sb.length());
                sb.append("get" + methodEnd);
                //����get ����
                getMethod = clazz.getDeclaredMethod(sb.toString(), new Class[]{});
                //����һ������������ �Ѷ�Ӧ���� propertyName �� get �� set �������浽������������
                pd = new PropertyDescriptor(propertyName, getMethod, setMethod);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pd;
    }

    public static void setProperty(Object obj, String propertyName, Object value) {
        Class clazz = obj.getClass();//��ȡ���������
        PropertyDescriptor pd = getPropertyDescriptor(clazz, propertyName);//��ȡ clazz �����е� propertyName ������������
        Method setMethod = pd.getWriteMethod();//�������������л�ȡ set ����
        try {
            setMethod.invoke(obj, new Object[]{value});//���� set �����������valueֵ����������ȥ
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getProperty(Object obj, String propertyName) {
        Class clazz = obj.getClass();//��ȡ���������
        PropertyDescriptor pd = getPropertyDescriptor(clazz, propertyName);//��ȡ clazz �����е� propertyName ������������
        Method getMethod = pd.getReadMethod();//�������������л�ȡ get ����
        Object value = null;
        try {
            value = getMethod.invoke(clazz, new Object[]{});//���÷�����ȡ�����ķ���ֵ
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
