package com.example.model;

import java.math.BigDecimal;

public class Dept {
	BigDecimal deptno;
	String dname;
	String loc;
	
	public BigDecimal getDeptno() {
		return deptno;
	}
	public void setDeptno(BigDecimal deptno) {
		this.deptno = deptno;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	@Override
	public String toString() {
		return "Dept [deptno=" + deptno + ", dname=" + dname + ", loc=" + loc
				+ "]";
	}
	
	
	
}
