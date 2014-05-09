
package tumblr.viewer.json;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Response {

    @Expose
    private Blog blog;
    @Expose
    private List<Post> posts = new ArrayList<Post>();
    @Expose
    private Long total_posts;

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Long getTotal_posts() {
        return total_posts;
    }

    public void setTotal_posts(Long total_posts) {
        this.total_posts = total_posts;
    }

}
