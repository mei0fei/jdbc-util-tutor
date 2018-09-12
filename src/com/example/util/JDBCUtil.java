package com.example.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCUtil {
	//可以将数据库的驱动以及连接信息变成static
	//因为是每一个的工具类对象都共享的
	//private static final String driverName = "com.mysql.jdbc.Driver";
	private static final String driverName = "oracle.jdbc.driver.OracleDriver";
	//private static final String url = "jdbc:mysql://192.168.6.70:3306/mydb?useUnicode=true&characterEncoding=UTF-8";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:orcl";	
	private static final String user = "scott";
	private static final String password = "tiger";
	PreparedStatement ppst = null;
	CallableStatement cstm = null;
	Connection conn = null;
	ResultSet rs = null;
	//实现连接，创建出ppst
	public void getPreparedStatement(String sql,Object...params){		
		try{
			//1、加载驱动包
			Class.forName(driverName);
			//2、开启连接		
			conn = DriverManager.getConnection(url, user, password);
			ppst = conn.prepareStatement(sql);
			int i=1;
			if(params!=null)
				for(Object o : params){
					ppst.setObject(i++, o);
				}
		}catch(Exception ex){
			System.out.println("数据库处理失败");
			ex.printStackTrace();
		}		
	}
	
	//实现连接，创建出cstm
	public CallableStatement getCallableStatement(String call){		
		try{
			//1、加载驱动包
			Class.forName(driverName);
			//2、开启连接		
			conn = DriverManager.getConnection(url, user, password);
			cstm = conn.prepareCall(call);
			
			return cstm;
		}catch(Exception ex){
			System.out.println("数据库处理失败");
			ex.printStackTrace();
		}	
		return null;
	}
	
	
	
	//执行查询--select
	public  ResultSet getResultSet(String sql,Object...params){			
		try {
			//先调用this.getPreparedStatement(sql);这个方法，真正生成ppst
			this.getPreparedStatement(sql,params);
			rs = ppst.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	//新增修改删除---insert,  update , delete, 
	public int executeUpdate(String sql, Object... params){
		int i=0;
		try {
			//先调用this.getPreparedStatement(sql);这个方法，真正生成ppst
			this.getPreparedStatement(sql,params);
			conn.setAutoCommit(false);//开启事务
			i =ppst.executeUpdate();
			conn.commit();//提交事务
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			this.doClose();
		}
		return i;
	}
	/**
	 * 将resultset转换成二维数组
	 * @param sql
	 * @param paramsh where 后面的条件
	 * @return
	 */
	public Object[][] getQueryResult(String sql, Object... params) {
		
		ResultSet rs = null;
		try {
			//
			rs = getResultSet(sql, params);
			ResultSetMetaData meta = rs.getMetaData(); // 获得元数据
			int m_Cols = meta.getColumnCount(); // 获得查询的列数个数
			int m_Rows = 0;
			Object obj;
			ArrayList list = new ArrayList();
			while (rs.next()) {
				m_Rows++;
				for (int i = 1; i <= m_Cols; i++) {
					obj = rs.getObject(i);
					list.add(obj);
				}

			}
			if (m_Rows == 0)
				return null;
			Object[] objAll = list.toArray();
			Object[][] objColRow = new Object[m_Rows][m_Cols];
			for (int iRow = 0; iRow < m_Rows; iRow++) {
				for (int iCol = 0; iCol < m_Cols; iCol++) {
					objColRow[iRow][iCol] = objAll[iRow * m_Cols + iCol];
				}
			}
			return objColRow;
		} catch (Exception ex) {
			return null;
		} finally {
			try {
				this.doClose();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/***
	 * 关闭所需的资源
	 */
	public void doClose(){
		 if(rs!=null){
			 try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 if(ppst!=null){
			 try {
				ppst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 if(cstm!=null){
			 try {
				cstm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 if(conn!=null){
			 try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	}
	
	//将结果集封装到List<T>, 从数据库中读取记录， 并转换为java对象， 存放到list集合中
	public <T> List<T> getQuery(String sql,Object obj,Object...params){
		List<T> list =null;
		//根据对象得到Class类型的对象
		Class clazz = obj.getClass();
		
		//clazz.getSimpleName();
		//得到结果集
		ResultSet rs =this.getResultSet(sql, params);
		//元数据
		try {
			list =new ArrayList();
			ResultSetMetaData rsmd = rs.getMetaData();
			//提前知道colum count
			int cnt = rsmd.getColumnCount();
			//遍历rs
			while(rs.next()){
				//先new一个对象
				T oNew = (T) clazz.newInstance();//直接用Class对象的newInstance()
				//这里完成给一个对象的所有属性赋值
				for(int i=1;i<=cnt;i++){
					String propertyName = rsmd.getColumnName(i).toLowerCase();
					//PropertyDescriptor pd = PropertyUtil.getPropertyDescriptor(clazz, propertyName);
					PropertyDescriptor pd = new PropertyDescriptor(propertyName,clazz);
					Method setMd = pd.getWriteMethod();
					//PropertyUtil.setProperty(oNew, propertyName, rs.getObject(propertyName));
					setMd.invoke(oNew, rs.getObject(propertyName));//读取rs的该列的值调用set方法赋值
				}
				list.add(oNew);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			this.doClose();
		}
		
		return list;
	}
	//通用版本的新增， 把对象添加到数据库表中
	public int doAdd(Object obj){
		int i=0;
		//构造sql语句
				// insert into tbname(c1,c2,...) values(?,?,?)
		Class c = obj.getClass();
		Field[] farr = c.getDeclaredFields();//field表示对象的属性
		//
		String sql = "insert into " + c.getSimpleName() + "(";
		String valStr = "values(";
		Object[] params = new Object[farr.length - 1];	
		try{
			for (Field f : farr) {
				if (!"id".equals(f.getName())) {
					sql += f.getName() + ",";
					valStr += "?,";					
					// 得到get方法，并且进行调用
					PropertyDescriptor pd = new PropertyDescriptor(f.getName(), c);
					// 利用pd对象得到get方法对象
					Method getMd = pd.getReadMethod();
					params[i++] = getMd.invoke(obj);
				}

			}
			System.out.println(sql);
			System.out.println(valStr);
			sql = sql.substring(0,sql.length()-1)+") ";
			valStr = valStr.substring(0,valStr.length()-1)+" ) ";
			// 循环结束后
			sql = sql + valStr;
			System.out.println(sql);
			//
			i = new JDBCUtil().executeUpdate(sql, params);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return i;
	}
	
}
