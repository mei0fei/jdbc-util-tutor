package com.example.model;

import java.math.BigDecimal;
import java.util.Date;

public class Emp {
	BigDecimal empno;
	String ename;
	String job;
	BigDecimal mgr;
	Date hiredate;
	BigDecimal sal;
	BigDecimal comm;
	BigDecimal deptno ;
	
	public BigDecimal getEmpno() {
		return empno;
	}
	public void setEmpno(BigDecimal empno) {
		this.empno = empno;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public BigDecimal getMgr() {
		return mgr;
	}
	public void setMgr(BigDecimal mgr) {
		this.mgr = mgr;
	}
	
	public BigDecimal getSal() {
		return sal;
	}
	public void setSal(BigDecimal sal) {
		this.sal = sal;
	}
	public BigDecimal getComm() {
		return comm;
	}
	public void setComm(BigDecimal comm) {
		this.comm = comm;
	}
	public BigDecimal getDeptno() {
		return deptno;
	}
	public void setDeptno(BigDecimal deptno) {
		this.deptno = deptno;
	}
	public Date getHiredate() {
		return hiredate;
	}
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}
	@Override
	public String toString() {
		return "Emp [empno=" + empno + ", ename=" + ename + ", job=" + job
				+ ", mgr=" + mgr + ", hiredate=" + hiredate + ", sal=" + sal
				+ ", comm=" + comm + ", deptno=" + deptno + "]";
	}
	
	
	
}
