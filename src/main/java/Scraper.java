import facebook4j.*;
import facebook4j.auth.AccessToken;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Scraper {
    private FacebookCompany facebookCompany;
    private Facebook facebook = new FacebookFactory().getInstance();

    public Scraper(FacebookCompany facebookCompany) {
        this.facebookCompany = facebookCompany;

        facebook.setOAuthAppId("", "");
        facebook.setOAuthAccessToken(new AccessToken(Main.accessTokenString));
    }

    private int getLikes(Page facebookPage) {
        Integer likes = facebookPage.getLikes();
        if (likes != null) {
            return likes;
        }
        return 0;
    }

    private int getTotalNumberOfPeople(Page facebookPage) {
        Integer totalNumber = facebookPage.getTalkingAboutCount();
        if (totalNumber != null) {
            return totalNumber;
        }
        return 0;
    }

    private int getLikesForPost(Post post){
        Integer totalLikes;
        try {
            Summary summary = facebook.getPostLikes(post.getId(), new Reading().limit(0).summary()).getSummary();
            if (summary != null) {
                totalLikes = summary.getTotalCount();
                if (totalLikes != null) {
                    return totalLikes;
                }
            }
        } catch (FacebookException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getCommentsForPost(Post post){
        Integer totalComments;
        try {
            Summary summary = facebook.getPostComments(post.getId(), new Reading().limit(0).summary()).getSummary();
            if (summary != null) {
                totalComments = summary.getTotalCount();
                if (totalComments != null) {
                    return totalComments;
                }
            }
        } catch (FacebookException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private List<Post> getPosts(String companyName){
        List<Post> posts = new ArrayList<Post>();
        try {
            ResponseList<facebook4j.Post> fetchedPosts = facebook.getPosts(facebook.getPage(companyName).getId(), new Reading().fields("id", "message", "link", "shares", "from", "created_time").limit(100));
            if (fetchedPosts != null) {
                Paging<Post> paging;
                do {
                    posts.addAll(fetchedPosts);
                    paging = fetchedPosts.getPaging();
                    System.out.println(String.format("Got %s posts from %s", posts.size(), facebookCompany.getLink()));
                } while ((paging != null) && ((fetchedPosts = facebook.fetchNext(paging)) != null));
            }
        } catch (FacebookException e) {
            e.printStackTrace();
        }

        return posts;
    }

    public Result getInformation() {
        Result result = new Result();

        try {
            final String companyNameFromUrl = facebook.getLink(facebookCompany.getLink(), new Reading().fields("id")).getId();

            Page facebookPage = facebook.getPage(companyNameFromUrl, new Reading().fields("name", "likes", "talking_about_count"));

            final String companyNameOnPage = facebookPage.getName();
            result.setNameOfTheCompany(facebookCompany.getName());
            result.setTotalLikesOnCompanyFBPage(getLikes(facebookPage));
            result.setTotalNumberOfPeopleWhoHaveBeenHere(getTotalNumberOfPeople(facebookPage));

            int count = 0;
            List<Post> posts = getPosts(companyNameFromUrl);

            for (Post post : posts) {
                Integer sharedCount = post.getSharesCount();
                String postAuthor = post.getFrom().getName();
                String message = post.getMessage();
                URL link = post.getLink();

                FacebookPost facebookPost = new FacebookPost();
                facebookPost.setNumberOfLikesForThePost(getLikesForPost(post));
                facebookPost.setNumberOfCommentsForThePost(getCommentsForPost(post));
                facebookPost.setNumberOfSharesForThePost((sharedCount != null) ? sharedCount : 0);
                facebookPost.setDateAndTimeOfPost(post.getCreatedTime());
                facebookPost.setWhoMadeThePost((companyNameOnPage.equals(postAuthor)) ? facebookCompany.getName() : postAuthor);
                facebookPost.setTextOfPost(((message != null) ? message : "") + " " + ((link != null) ? link.toString() : ""));

                result.addPosts(facebookPost);

                if (count % 25 == 0) System.out.println(count + " / " + posts.size());
                count++;
            }
        } catch (FacebookException e) {
            System.err.println(String.format("Page %s with ID = %s not found or other error", facebookCompany.getLink(), facebookCompany.getId()));
            result = null;
        }

        return result;
    }
}