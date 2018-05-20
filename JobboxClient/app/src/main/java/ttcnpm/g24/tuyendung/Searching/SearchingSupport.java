package ttcnpm.g24.tuyendung.Searching;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.List;

public class SearchingSupport {
    private Bitmap avatar;
    private String fullname;
    private String detail;
    private String id;
    private String type;
    private String isFollow;

    public SearchingSupport (Bitmap avt, String fullname, String detail, String id,String type,String isFollow){
        this.avatar = avt;
        this.fullname = fullname;
        this.detail = detail;
        this.id = id;
        this.type=type;
        this.isFollow=isFollow;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getType(){return type;}
    public String getIsFollow(){return isFollow;}
}
