package com.example.dao;

import java.util.Arrays;
import java.util.List;

import com.example.model.Dept;
import com.example.util.JDBCUtil;

public class DeptDao {
	
	public Object[][] getAllDept1(){
		
		JDBCUtil util = new JDBCUtil();
		//List<Dept> depts =  util.getQuery("select deptno, dname, loc from dept", new Dept(), null);
		Object[][] objs = util.getQueryResult("select deptno, dname, loc from dept");
		//for(Dept d : depts){
		//	System.out.println(d);
		//}
		for(int i=0;i<objs.length;i++){
			System.out.println(Arrays.toString(objs[i]));
		}
		//System.out.println(objs);
		return objs;
		
	}
	
	public List<Dept> getAllDept2(){
		
		JDBCUtil util = new JDBCUtil();
		List<Dept> depts =  util.getQuery("select deptno, dname, loc from dept", new Dept());
		
		for(Dept d : depts){
			System.out.println(d);
		}
		
		
		return depts;
		
	}
	
	public int insert(){
		
		String sql="insert into dept values(?, ?, ? )";
		//5.执行查询
		JDBCUtil util = new JDBCUtil();
		int n=util.executeUpdate(sql, 50, "cleaner", "wheris");
		if(n>0){
			System.out.println("success");
		}else{
			System.out.println("error");
		}
		return n;
	}
	
	public int delete(){
		String sql="delete from dept  where deptno = 50";
		//5.执行查询
		JDBCUtil util = new JDBCUtil();
		int n=util.executeUpdate(sql);
		if(n>0){
			System.out.println("success");
		}else{
			System.out.println("error");
		}
		return n;
	}
	
	
	
	public static void main(String[] args){
		DeptDao dao = new DeptDao();
		dao.getAllDept2();
		//dao.insert();
		//dao.delete();
		//dao.insert();
		//dao.delete();
		
	}
	
}
