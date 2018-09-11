package com.example.dao;

import java.util.Arrays;
import java.util.List;

import com.example.model.Dept;
import com.example.model.Emp;
import com.example.util.JDBCUtil;

public class EmpDao {
	public Object[][] select(){
		
		JDBCUtil util = new JDBCUtil();
		
		Object[][] objs = util.getQueryResult("select * from emp");
		
		for(int i=0;i<objs.length;i++){
			System.out.println(Arrays.toString(objs[i]));
		}
		
		return objs;
		
	}
	
	public List<Emp> select2(){
		
		JDBCUtil util = new JDBCUtil();
		List<Emp> emps =  util.getQuery("select * from emp", new Emp());
		
		for(Emp d : emps){
			System.out.println(d);
		}
		
		
		
		return emps;
		
	}
	
	public int insert(){
		String sql="insert into emp values(8007,'小红','waiter',7369,to_date('2018/07/18','YYYY/MM/DD'),1500,1000,10)";
		
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
	
	public int delete(){
		String sql="delete from emp  where empno = ?";
		//5.执行查询
		JDBCUtil util = new JDBCUtil();
		int n=util.executeUpdate(sql, 8004);
		if(n>0){
			System.out.println("success");
		}else{
			System.out.println("error");
		}
		return n;
	}
	
	public int update(){
		String sql="update emp set sal=1600  where empno = ?";
		//5.执行查询
		JDBCUtil util = new JDBCUtil();
		int n=util.executeUpdate(sql, 8005);
		if(n>0){
			System.out.println("success");
		}else{
			System.out.println("error");
		}
		return n;
	}
	
	public static void main(String[] args){
		EmpDao dao = new EmpDao();
		dao.select2();
	}
	
}
