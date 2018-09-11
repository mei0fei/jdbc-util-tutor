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
	
	public static void main(String[] args){
		EmpDao dao = new EmpDao();
		dao.select2();
	}
	
}
