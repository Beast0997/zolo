package zolostays.in.zolostays;

/**
 * Created by gulshan on 15/07/17.
 */

public class ListBean {

    private String id, title , url;



    public ListBean() {

    }


    public ListBean(String id ,String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }
}
