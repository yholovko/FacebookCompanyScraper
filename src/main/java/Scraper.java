import facebook4j.*;
import facebook4j.Post;
import facebook4j.auth.AccessToken;

import java.util.ArrayList;
import java.util.List;

public class Scraper {
    final private String accessTokenString = "CAACEdEose0cBAKNX4VYeZBgnu3u5QrDbh6TZAdHBhxum2kami0nYhWGWjWMcMuNsAFQpm1v72wZCnEesL19UczqZAzaLTTWjNU2UjA8eUElIxOZCgJ0ToPKT0MZAf4HeeyEHMExXT2mb9jV79mcX5UrB4gL3rxldaGzNMfizZCU86l4PfycEU6uXcR4IAOdUN0seuYqJyPS088UHGhVL4mIJG3OEM8XKO8ZD";
    private FacebookCompany facebookCompany;
    private Result result = new Result();
    private Facebook facebook = new FacebookFactory().getInstance();

    public Scraper(FacebookCompany facebookCompany) {
        this.facebookCompany = facebookCompany;

        facebook.setOAuthAppId("", "");
        facebook.setOAuthAccessToken(new AccessToken(accessTokenString));
    }

    private int getLikes(String companyName){
        try {
            return facebook.getPage(companyName).getLikes();
        } catch (FacebookException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getTotalNumberOfPeople(String companyName){
        try {
            return facebook.getPage(companyName).getTalkingAboutCount();
        } catch (FacebookException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getLikesForPost(Post post){
        int count = 0;
        PagableList<Like> fetchedLikes = null;
        try {
            fetchedLikes = post.getLikes();
            Paging<Like> paging;
            do {
                count += fetchedLikes.size();
                paging = fetchedLikes.getPaging();
            } while ((paging != null) && ((fetchedLikes = facebook.fetchNext(paging)) != null));
        } catch (FacebookException e) {
            e.printStackTrace();
        }
        return count;
    }

    private int getCommentsForPost(Post post){
        int count = 0;
        PagableList<Comment> fetchedComments = null;
        try {
            fetchedComments = post.getComments();
            Paging<Comment> paging;
            do {
                count += fetchedComments.size();
                paging = fetchedComments.getPaging();
            } while ((paging != null) && ((fetchedComments = facebook.fetchNext(paging)) != null));
        } catch (FacebookException e) {
            e.printStackTrace();
        }
        return count;
    }

    private List<Post> getPosts(String companyName){
        List<Post> posts = new ArrayList<Post>();
        try {
            ResponseList<facebook4j.Post> fetchedPosts = facebook.getPosts(facebook.getPage(companyName).getId(), new Reading().limit(100));
            Paging<Post> paging;
            do {
                posts.addAll(fetchedPosts);
                paging = fetchedPosts.getPaging();
            } while ((paging != null) && ((fetchedPosts = facebook.fetchNext(paging)) != null));
        } catch (FacebookException e) {
            e.printStackTrace();
        }

        return posts;
    }

    public Result getInformation(){
        final String companyName = facebookCompany.getLink().replaceAll("https://www.facebook.com/","");

        result.setNameOfTheCompany(facebookCompany.getName());
        result.setTotalLikesOnCompanyFBPage(getLikes(companyName));
        result.setTotalNumberOfPeopleWhoHaveBeenHere(getTotalNumberOfPeople(companyName));

        FacebookPost facebookPost = new FacebookPost();

        for (Post post : getPosts(companyName)) {
            facebookPost.setNumberOfLikesForThePost(getLikesForPost(post));
            facebookPost.setNumberOfCommentsForThePost(getCommentsForPost(post));
            facebookPost.setNumberOfSharesForThePost(post.getSharesCount());
            facebookPost.setDateAndTimeOfPost(post.getCreatedTime());
            facebookPost.setWhoMadeThePost(post.getFrom().getName());
            facebookPost.setTextOfPost(post.getMessage() + " " + post.getLink());

            result.addPosts(facebookPost);
        }
        return result;
    }
}