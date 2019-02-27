package com.ebay.bo;

public class Bo_Database 
{
	String db_Schema;
	
	String user ;
	
	String DB_password ;
	
	String DB_URL;
	
	String MembershipID;
	
	

	public String getMembershipID()
	{
		return MembershipID;
	}
	
	public void setMembershipID(String MembershipID)
	{
		this.MembershipID = MembershipID;
	}

	public String getDB_URL() 
	{
		return DB_URL;
	}

	public void setDB_URL(String DB_URL) {
		this.DB_URL = DB_URL;
	}
	
	public String getdb_Schema() 
	{
		return db_Schema;
	}

	public void setdb_Schema(String db_Schema) {
		this.db_Schema = db_Schema;
	}
	
	public String getuser()
	{
		return user;
	}
	
    public void setuser(String DB_User)
    {
    	this.user = DB_User;
    }

    public String getDB_password()
	{
		return DB_password;
	}
	
    public void setpassword(String DB_Password)
    {
    	this.DB_password = DB_Password;
    }

}
