package ttcnpm.g24.tuyendung.Newsfeed;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.List;

public class News {
    private Bitmap avatar;
    private String company_name;
    private String news_title;
    private String news_time;
    private String positions;
    private String news_content;

    public News (Bitmap avt, String com_name, String title, String time, String positions, String content){
        this.avatar = avt;
        this.company_name = com_name;
        this.news_title = title;
        this.news_time = time;
        this.positions = positions;
        this.news_content = content;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_time() {
        return news_time;
    }

    public void setNews_time(String news_time) {
        this.news_time = news_time;
    }

    public String getPositions() { return positions; }

    public void setPositions(String positions) { this.positions = positions; }

    public String getNews_content() { return news_content; }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }
}
