import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FacebookPost {
    private String textOfPost;
    private Date dateAndTimeOfPost;
    private String whoMadeThePost;
    private int numberOfLikesForThePost;
    private int numberOfSharesForThePost;
    private int numberOfCommentsForThePost;

    public String getTextOfPost() {
        return textOfPost;
    }

    public void setTextOfPost(String textOfPost) {
        this.textOfPost = textOfPost;
    }

    public String getDateOfPost() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");

        return dateFormat.format(dateAndTimeOfPost);
    }

    public String getTimeOfPost() {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String time24 = timeFormat.format(dateAndTimeOfPost);

        try {
            return new SimpleDateFormat("K:mm a").format(new SimpleDateFormat("H:mm").parse(time24));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public void setDateAndTimeOfPost(Date time) {
        this.dateAndTimeOfPost = time;
    }

    public String getWhoMadeThePost() {
        return whoMadeThePost;
    }

    public void setWhoMadeThePost(String whoMadeThePost) {
        this.whoMadeThePost = whoMadeThePost;
    }

    public int getNumberOfLikesForThePost() {
        return numberOfLikesForThePost;
    }

    public void setNumberOfLikesForThePost(int numberOfLikesForThePost) {
        this.numberOfLikesForThePost = numberOfLikesForThePost;
    }

    public int getNumberOfSharesForThePost() {
        return numberOfSharesForThePost;
    }

    public void setNumberOfSharesForThePost(int numberOfSharesForThePost) {
        this.numberOfSharesForThePost = numberOfSharesForThePost;
    }

    public int getNumberOfCommentsForThePost() {
        return numberOfCommentsForThePost;
    }

    public void setNumberOfCommentsForThePost(int numberOfCommentsForThePost) {
        this.numberOfCommentsForThePost = numberOfCommentsForThePost;
    }
}