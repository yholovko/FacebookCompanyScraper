import java.util.ArrayList;
import java.util.List;

public class Result {
    private String nameOfTheCompany;
    private int totalLikesOnCompanyFBPage;
    private int totalNumberOfPeopleWhoHaveBeenHere;
    private List<FacebookPost> posts = new ArrayList<FacebookPost>();

    public String getNameOfTheCompany() {
        return nameOfTheCompany;
    }

    public void setNameOfTheCompany(String nameOfTheCompany) {
        this.nameOfTheCompany = nameOfTheCompany;
    }

    public int getTotalLikesOnCompanyFBPage() {
        return totalLikesOnCompanyFBPage;
    }

    public void setTotalLikesOnCompanyFBPage(int totalLikesOnCompanyFBPage) {
        this.totalLikesOnCompanyFBPage = totalLikesOnCompanyFBPage;
    }

    public int getTotalNumberOfPeopleWhoHaveBeenHere() {
        return totalNumberOfPeopleWhoHaveBeenHere;
    }

    public void setTotalNumberOfPeopleWhoHaveBeenHere(int totalNumberOfPeopleWhoHaveBeenHere) {
        this.totalNumberOfPeopleWhoHaveBeenHere = totalNumberOfPeopleWhoHaveBeenHere;
    }

    public List<FacebookPost> getPosts() {
        return posts;
    }

    public void addPosts(FacebookPost post) {
        this.posts.add(post);
    }
}
