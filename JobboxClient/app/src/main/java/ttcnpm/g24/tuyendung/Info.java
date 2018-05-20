package ttcnpm.g24.tuyendung;

import android.provider.ContactsContract;

import java.text.DecimalFormat;
import java.util.Date;

public class Info {
    //public final DecimalFormat MONEY =new DecimalFormat("$#,##0.00");
    private String username;
    private String password;
    private String name;
    private  String address;
    private String phone;
    private String type;
    private String series;
    private String datefound;
    private String field;
    private String desc;
    private String birthday;
    private String sex;
    private String university;
    private String major;
    private String expt;
    public Info(){
       setAddress(null);
       setBirthday(null);
       setDatefound(null);
       setDesc(null);

       setExpt(null);
       setField(null);
       setMajor(null);
       setname(null);
       setPassword(null);
       setPhone(null);
       setSeries(null);
       setSex(null);

       setUniversity(null);
       setUsername(null);
    }
    public void setUsername(String newusername){
        username=newusername;
    }
    public void setPassword(String newpassword){
        password=newpassword;
    }
    public void setname(String newname){
         name=newname;
    }
    public void setAddress(String newaddr){
       address=newaddr;
    }
    public void setPhone(String newtel){
        phone=newtel;
    }
    public void setType(String newtype){
        type=newtype;
    }
    public void setSeries(String newseries){
        series=newseries;
    }
    public void setSex(String newsex){
        sex=newsex;
    }
    public void setDatefound(String newsdate){
        datefound=newsdate;
    }
    public void setField(String newfield){
         field=newfield;
    }
    public void setDesc(String newdesc){
         desc=newdesc;
    }
    public void setBirthday(String newsdate){
         birthday=newsdate;
    }
    public void setUniversity(String newuniv){
         university=newuniv;
    }
    public void setMajor(String newmaj){
         major=newmaj;
    }
    public void setExpt(String newsdate){
         expt=newsdate;
    }

    public String getAddress() {
        return address;
    }
    public String getUsername() {
        return username;
    }

    public String getDesc() {
        return desc;
    }

    public String getField() {
        return field;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPassword() {
        return password;
    }

    public String getSeries() {
        return series;
    }

    public String getDatefound() {
        return datefound;
    }

    public String getPhone() {
        return phone;
    }

    public String getExpt() {
        return expt;
    }

    public String getType() {
        return type;
    }

    public String getSex() {
        return sex;
    }

    public String getUniversity() {
        return university;
    }

    public String getMajor() {
        return major;
    }

}
