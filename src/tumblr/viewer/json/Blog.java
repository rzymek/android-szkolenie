
package tumblr.viewer.json;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Blog {

    @Expose
    private String title;
    @Expose
    private String name;
    @Expose
    private Long posts;
    @Expose
    private String url;
    @Expose
    private Long updated;
    @Expose
    private String description;
    @Expose
    private Boolean ask;
    @Expose
    private String ask_page_title;
    @Expose
    private Boolean ask_anon;
    @Expose
    private Boolean is_nsfw;
    @Expose
    private Boolean share_likes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPosts() {
        return posts;
    }

    public void setPosts(Long posts) {
        this.posts = posts;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAsk() {
        return ask;
    }

    public void setAsk(Boolean ask) {
        this.ask = ask;
    }

    public String getAsk_page_title() {
        return ask_page_title;
    }

    public void setAsk_page_title(String ask_page_title) {
        this.ask_page_title = ask_page_title;
    }

    public Boolean getAsk_anon() {
        return ask_anon;
    }

    public void setAsk_anon(Boolean ask_anon) {
        this.ask_anon = ask_anon;
    }

    public Boolean getIs_nsfw() {
        return is_nsfw;
    }

    public void setIs_nsfw(Boolean is_nsfw) {
        this.is_nsfw = is_nsfw;
    }

    public Boolean getShare_likes() {
        return share_likes;
    }

    public void setShare_likes(Boolean share_likes) {
        this.share_likes = share_likes;
    }

}
