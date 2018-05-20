package ttcnpm.g24.tuyendung.FollowedCompanies;

import android.widget.ImageView;

public class FollowedCompany {
    private ImageView avatar;
    private String company_name;
    private String company_username;

    public FollowedCompany (ImageView avatar, String company_name, String company_username) {
        this.avatar = avatar;
        this.company_name = company_name;
        this.company_username = company_username;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setCompany_username(String company_username) {
        this.company_username = company_username;
    }

    public String getCompany_username() {
        return company_username;
    }

    public ImageView getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageView avatar) {
        this.avatar = avatar;
    }
}
