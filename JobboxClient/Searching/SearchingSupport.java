package ttcnpm.g24.tuyendung.Searching;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.List;

public class SearchingSupport {
    private Bitmap avatar;
    private String fullname;
    private String detail;
    private String id;
    private String news_content;

    public SearchingSupport (Bitmap avt, String fullname, String detail, String id, String content){
        this.avatar = avt;
        this.fullname = fullname;
        this.detail = detail;
        this.id = id;
        this.news_content = content;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public String getCompany_name() {
        return fullname;
    }

    public void setCompany_name(String fullname) {
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

    public String getNews_content() { return news_content; }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }
}
