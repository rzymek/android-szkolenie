
package tumblr.viewer.json;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Post {

    @Expose
    private String blog_name;
    @Expose
    private Long id;
    @Expose
    private String post_url;
    @Expose
    private String slug;
    @Expose
    private String type;
    @Expose
    private String date;
    @Expose
    private Long timestamp;
    @Expose
    private String state;
    @Expose
    private String format;
    @Expose
    private String reblog_key;
    @Expose
    private List<String> tags = new ArrayList<String>();
    @Expose
    private String short_url;
    @Expose
    private String post_author;
    @Expose
    private List<Object> highlighted = new ArrayList<Object>();
    @Expose
    private Boolean bookmarklet;
    @Expose
    private Long note_count;
    @Expose
    private String source_url;
    @Expose
    private String source_title;
    @Expose
    private String caption;
    @Expose
    private String link_url;
    @Expose
    private String image_permalink;
    @Expose
    private List<Photo> photos = new ArrayList<Photo>();

    public String getBlog_name() {
        return blog_name;
    }

    public void setBlog_name(String blog_name) {
        this.blog_name = blog_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPost_url() {
        return post_url;
    }

    public void setPost_url(String post_url) {
        this.post_url = post_url;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getReblog_key() {
        return reblog_key;
    }

    public void setReblog_key(String reblog_key) {
        this.reblog_key = reblog_key;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }

    public String getPost_author() {
        return post_author;
    }

    public void setPost_author(String post_author) {
        this.post_author = post_author;
    }

    public List<Object> getHighlighted() {
        return highlighted;
    }

    public void setHighlighted(List<Object> highlighted) {
        this.highlighted = highlighted;
    }

    public Boolean getBookmarklet() {
        return bookmarklet;
    }

    public void setBookmarklet(Boolean bookmarklet) {
        this.bookmarklet = bookmarklet;
    }

    public Long getNote_count() {
        return note_count;
    }

    public void setNote_count(Long note_count) {
        this.note_count = note_count;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getSource_title() {
        return source_title;
    }

    public void setSource_title(String source_title) {
        this.source_title = source_title;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public String getImage_permalink() {
        return image_permalink;
    }

    public void setImage_permalink(String image_permalink) {
        this.image_permalink = image_permalink;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
    @Override
    public String toString() {
    	return caption;
    }
}
