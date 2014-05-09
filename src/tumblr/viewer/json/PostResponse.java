
package tumblr.viewer.json;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class PostResponse {

    @Expose
    private Meta meta;
    @Expose
    private Response response;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
