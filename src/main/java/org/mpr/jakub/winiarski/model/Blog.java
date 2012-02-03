package org.mpr.jakub.winiarski.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import org.mpr.jakub.winiarski.exceptions.PostIsNullException;

@Entity
public class Blog implements Serializable {

    private Long id;
    private String subject;
    private String header;
    private List<Post> posts = new ArrayList<Post>();;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
    
    @OneToMany(mappedBy="blog")
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
    
    public void addPost(Post post) throws PostIsNullException {
        if(post == null) { throw new PostIsNullException(); }
        posts.add(post);
        post.setBlog(this);
    }
}
